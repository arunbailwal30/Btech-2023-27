/*
 * Copyright (c) 2017, 2025, Oracle and/or its affiliates.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License, version 2.0, as published by
 * the Free Software Foundation.
 *
 * This program is designed to work with certain software that is licensed under separate terms, as designated in a particular file or component or in
 * included license documentation. The authors of MySQL hereby grant you an additional permission to link the program and your derivative works with the
 * separately licensed software that they have either included with the program or referenced in the documentation.
 *
 * Without limiting anything contained in the foregoing, this file, which is part of MySQL Connector/J, is also subject to the Universal FOSS Exception,
 * version 1.0, a copy of which can be found at http://oss.oracle.com/licenses/universal-foss-exception.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License, version 2.0, for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */

package com.mysql.cj;

import static com.mysql.cj.PlaceholderPurpose.GENERIC;
import static com.mysql.cj.PlaceholderPurpose.INSERT_VALUES;
import static com.mysql.cj.PlaceholderPurpose.LIMIT_AND_OFFSET;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.util.SearchMode;
import com.mysql.cj.util.StringInspector;
import com.mysql.cj.util.StringUtils;

/**
 * Represents the "parsed" state of a prepared query, with the statement broken up into its static and dynamic (where parameters are bound) parts.
 */
public class QueryInfo {

    private static final String OPENING_MARKERS = "`'\"";
    private static final String CLOSING_MARKERS = "`'\"";
    private static final String OVERRIDING_MARKERS = "";

    private static final String SELECT_STATEMENT = "SELECT";
    private static final String TABLE_STATEMENT = "TABLE";
    private static final String INSERT_STATEMENT = "INSERT";
    private static final String REPLACE_STATEMENT = "REPLACE";
    private static final String MULTIPLE_QUERIES_TAG = "(multiple queries)";

    private static final String LIMIT_CLAUSE = "LIMIT";
    private static final String OFFSET_CLAUSE = "OFFSET";
    private static final String VALUE_CLAUSE = "VALUE";
    private static final String AS_CLAUSE = "AS";
    private static final String[] ODKU_CLAUSE = new String[] { "ON", "DUPLICATE", "KEY", "UPDATE" };
    private static final String LAST_INSERT_ID_FUNC = "LAST_INSERT_ID";

    private QueryInfo baseQueryInfo = null;

    private String sql;
    private String encoding;
    private QueryReturnType queryReturnType = null;
    private int queryLength = 0;
    private int queryStartPos = 0;
    private char statementFirstChar = Character.MIN_VALUE;
    private String statementKeyword = "";
    private int batchCount = 1;
    private int numberOfPlaceholders = 0;
    private int numberOfQueries = 0;
    private boolean containsOnDuplicateKeyUpdate = false;
    private boolean isRewritableWithMultiValuesClause = false;
    private int valuesClauseLength = -1;
    private ArrayList<Integer> valuesEndpoints = new ArrayList<>();
    private byte[][] staticSqlParts = null;
    private List<PlaceholderPurpose> placeholderPurposes = new ArrayList<>();

    /**
     * Constructs a {@link QueryInfo} object for the given query or multi-query. The parsed result of this query allows to determine the location of the
     * placeholders, the query static parts and whether this query can be rewritten as a multi-values clause query.
     *
     * @param sql
     *            the query SQL string to parse and analyze
     * @param session
     *            the {@link Session} under which the query analysis must be done.
     * @param encoding
     *            the characters encoding to use when extracting the query static parts as byte arrays.
     */
    public QueryInfo(String sql, Session session, String encoding) {
        if (sql == null) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("QueryInfo.NullSql"), session.getExceptionInterceptor());
        }

        this.baseQueryInfo = this;

        this.sql = sql;
        this.encoding = encoding;

        boolean noBackslashEscapes = session.getServerSession().isNoBackslashEscapesSet();
        boolean rewriteBatchedStatements = session.getPropertySet().getBooleanProperty(PropertyKey.rewriteBatchedStatements).getValue();
        boolean dontCheckOnDuplicateKeyUpdateInSQL = session.getPropertySet().getBooleanProperty(PropertyKey.dontCheckOnDuplicateKeyUpdateInSQL).getValue();

        this.queryReturnType = getQueryReturnType(this.sql, noBackslashEscapes);
        this.queryLength = this.sql.length();

        StringInspector strInspector = new StringInspector(this.sql, OPENING_MARKERS, CLOSING_MARKERS, OVERRIDING_MARKERS,
                noBackslashEscapes ? SearchMode.__MRK_COM_MYM_HNT_WS : SearchMode.__BSE_MRK_COM_MYM_HNT_WS);

        this.queryStartPos = strInspector.indexOfNextAlphanumericChar(); // Skip comments at the beginning of queries.
        if (this.queryStartPos == -1) {
            this.queryStartPos = this.queryLength;
        } else {
            this.numberOfQueries = 1;
            this.statementFirstChar = Character.toUpperCase(strInspector.getChar());

            // Capture the statement keyword.
            int endStatementKeyword = 0;
            int nextChar = this.queryStartPos;
            StringBuilder sbStatementKeyword = new StringBuilder();
            do {
                sbStatementKeyword.append(Character.toUpperCase(strInspector.getChar()));
                endStatementKeyword = nextChar + 1;
                strInspector.incrementPosition();
                nextChar = strInspector.indexOfNextChar();
            } while (nextChar == endStatementKeyword);
            this.statementKeyword = sbStatementKeyword.toString();
        }

        // Check if should look for LIMIT and OFFSET clauses, i.e., if it is a SELECT or TABLE statement.
        boolean lookForLimitAndOffset = false;

        // Only INSERT and REPLACE statements support multi-values clause rewriting.
        boolean isInsert = false;
        boolean isReplace = false;

        switch (this.statementKeyword) {
            case SELECT_STATEMENT:
            case TABLE_STATEMENT:
                lookForLimitAndOffset = true;
                break;
            case INSERT_STATEMENT:
                isInsert = true;
                break;
            case REPLACE_STATEMENT:
                isReplace = true;
                break;
        }

        // Check if the statement has potential to be rewritten as a multi-values clause statement, i.e., if it is an INSERT or REPLACE statement and
        // 'rewriteBatchedStatements' is enabled.
        boolean rewritableAsMultiValues = (isInsert || isReplace) && rewriteBatchedStatements;

        // Check if should look for ON DUPLICATE KEY UPDATE clause, i.e., if it is an INSERT statement and 'dontCheckOnDuplicateKeyUpdateInSQL' is disabled.
        // 'rewriteBatchedStatements=true' cancels any value specified in 'dontCheckOnDuplicateKeyUpdateInSQL'.
        boolean lookForOnDuplicateKeyUpdate = isInsert && (!dontCheckOnDuplicateKeyUpdateInSQL || rewriteBatchedStatements);

        // Scan placeholders.
        int generalEndpointStart = 0;
        int valuesEndpointStart = 0;
        int valuesClauseBegin = -1;
        boolean valuesClauseBeginFound = false;
        int valuesClauseEnd = -1;
        boolean valuesClauseEndFound = false;
        boolean withinValuesClause = false;
        boolean valueStrMayBeTableName = true;
        boolean matchedLimitClause = false;
        boolean withinLimitClause = false;
        int parensLevel = 0;
        int matchEnd = -1;
        int lastPos = -1;
        char lastChar = 0;

        // Endpoints for the satement's static sections (parts around placeholders).
        ArrayList<Integer> staticEndpoints = new ArrayList<>();

        while (strInspector.indexOfNextChar() != -1) {
            int currPos = strInspector.getPosition();
            char currChar = strInspector.getChar();

            if (currChar == '?') { // Process placeholder.
                valueStrMayBeTableName = false; // At this point a string "VALUE" cannot be a table name.

                this.numberOfPlaceholders++;
                int endpointEnd = strInspector.getPosition();
                staticEndpoints.add(generalEndpointStart);
                staticEndpoints.add(endpointEnd);
                this.placeholderPurposes.add(withinValuesClause ? INSERT_VALUES : withinLimitClause ? LIMIT_AND_OFFSET : GENERIC);
                strInspector.incrementPosition();
                generalEndpointStart = strInspector.getPosition(); // Next section starts after the placeholder.

                if (rewritableAsMultiValues) {
                    if (!valuesClauseBeginFound) { // There's a placeholder before the VALUE[S] clause.
                        rewritableAsMultiValues = false;
                    } else if (valuesClauseEndFound) { // There's a placeholder after the end of the VALUE[S] clause.
                        rewritableAsMultiValues = false;
                    } else if (withinValuesClause) {
                        this.valuesEndpoints.add(valuesEndpointStart);
                        this.valuesEndpoints.add(endpointEnd);
                        valuesEndpointStart = generalEndpointStart;
                    }
                }

            } else if (currChar == ';') { // Multi-query SQL.
                valueStrMayBeTableName = false; // At this point a string "VALUE" cannot be a table name.
                matchedLimitClause = false;
                withinLimitClause = false;

                strInspector.incrementPosition();
                if (strInspector.indexOfNextNonWsChar() != -1) {
                    this.numberOfQueries++;

                    if (rewritableAsMultiValues) {
                        rewritableAsMultiValues = false;
                        valuesClauseBeginFound = false;
                        valuesClauseBegin = -1;
                        valuesClauseEndFound = false;
                        valuesClauseEnd = -1;
                        withinValuesClause = false;
                        parensLevel = 0;
                    }

                    isInsert = false;
                    isReplace = false;
                    // Check if continue looking for ON DUPLICATE KEY UPDATE.
                    if (dontCheckOnDuplicateKeyUpdateInSQL || this.containsOnDuplicateKeyUpdate) {
                        lookForOnDuplicateKeyUpdate = false;
                    } else {
                        isInsert = strInspector.matchesIgnoreCase(INSERT_STATEMENT) != -1;
                        if (isInsert) {
                            strInspector.incrementPosition(INSERT_STATEMENT.length() - 1); // Advance to the end of "INSERT" and capture last character.
                            currPos = strInspector.getPosition();
                            currChar = strInspector.getChar();
                            strInspector.incrementPosition();
                        }
                        lookForOnDuplicateKeyUpdate = isInsert;
                    }

                    // Check if continue looking for LIMIT and OFFSET.
                    if (!isInsert && !isReplace && ((matchEnd = strInspector.matchesIgnoreCase(SELECT_STATEMENT)) != -1
                            || (matchEnd = strInspector.matchesIgnoreCase(TABLE_STATEMENT)) != -1)) {
                        strInspector.incrementPosition(matchEnd - strInspector.getPosition() - 1); // Advance to the end and capture last character.
                        currPos = strInspector.getPosition();
                        currChar = strInspector.getChar();
                        strInspector.incrementPosition();

                        lookForLimitAndOffset = true;
                    } else {
                        lookForLimitAndOffset = false;
                    }
                }

            } else {
                if (rewritableAsMultiValues) {
                    if ((!valuesClauseBeginFound || valueStrMayBeTableName) && strInspector.matchesIgnoreCase(VALUE_CLAUSE) != -1) { // VALUE[S] clause found.
                        boolean leftBounded = currPos > lastPos + 1 || lastChar == ')'; // ')' would mark the ending of the columns list: "...)VALUES...".

                        strInspector.incrementPosition(VALUE_CLAUSE.length() - 1); // Advance to the end of "VALUE" and capture last character.
                        currPos = strInspector.getPosition();
                        currChar = strInspector.getChar();
                        strInspector.incrementPosition();
                        boolean matchedValues = false;
                        if (strInspector.matchesIgnoreCase("S") != -1) { // Check for the "S" in "VALUE(S)" and advance 1 more character if so.
                            currPos = strInspector.getPosition();
                            currChar = strInspector.getChar();
                            strInspector.incrementPosition();
                            matchedValues = true;
                        }

                        int endPos = strInspector.getPosition();
                        int nextPos = strInspector.indexOfNextChar(); // Position on the first meaningful character after VALUE[S].
                        boolean rightBounded = nextPos > endPos || strInspector.getChar() == '('; // '(' would mark the beginning of VALUE[S]: "... VALUES(...".

                        if (leftBounded && rightBounded) { // VALUE[S] keyword must not be part of another string, such as a table or column name.
                            if (matchedValues) {
                                valueStrMayBeTableName = false; // At this point a string "VALUE" cannot be a table name.
                            }
                            if (matchedValues && this.containsOnDuplicateKeyUpdate) { // VALUES after ODKU is a function, not a clause.
                                rewritableAsMultiValues = false;
                            } else {
                                withinValuesClause = true;
                                valuesClauseBegin = strInspector.getPosition();
                                valuesClauseBeginFound = true;
                                valuesEndpointStart = valuesClauseBegin;
                            }
                        }

                    } else if (withinValuesClause && currChar == '(') {
                        parensLevel++;
                        strInspector.incrementPosition();

                    } else if (withinValuesClause && currChar == ')') {
                        parensLevel--;
                        if (parensLevel < 0) {
                            parensLevel = 0; // Keep going, not checking for syntax validity.
                        }
                        strInspector.incrementPosition();
                        valuesClauseEnd = strInspector.getPosition(); // It may not be the end of the VALUE[S] clause yet but save it for later.

                    } else if (withinValuesClause && parensLevel == 0 && isInsert //
                            && strInspector.matchesIgnoreCase(AS_CLAUSE) != -1) { // End of VALUE[S] clause.
                        valueStrMayBeTableName = false; // At this point a string "VALUE" cannot be a table name.

                        if (valuesClauseEnd == -1) {
                            valuesClauseEnd = strInspector.getPosition();
                        }
                        valuesClauseEndFound = true;
                        withinValuesClause = false;
                        strInspector.incrementPosition(AS_CLAUSE.length() - 1); // Advance to the end of "AS" and capture last character.
                        currPos = strInspector.getPosition();
                        currChar = strInspector.getChar();
                        strInspector.incrementPosition();

                        this.valuesEndpoints.add(valuesEndpointStart);
                        this.valuesEndpoints.add(valuesClauseEnd);

                    } else if (withinValuesClause && parensLevel == 0 && isInsert //
                            && (matchEnd = strInspector.matchesIgnoreCase(ODKU_CLAUSE)) != -1) { // End of VALUE[S] clause.
                        valueStrMayBeTableName = false; // At this point a string "VALUE" cannot be a table name.

                        if (valuesClauseEnd == -1) {
                            valuesClauseEnd = strInspector.getPosition();
                        }
                        valuesClauseEndFound = true;
                        withinValuesClause = false;
                        strInspector.incrementPosition(matchEnd - strInspector.getPosition() - 1); // Advance to the end of "ODKU" and capture last character.
                        currPos = strInspector.getPosition();
                        currChar = strInspector.getChar();
                        strInspector.incrementPosition();

                        this.valuesEndpoints.add(valuesEndpointStart);
                        this.valuesEndpoints.add(valuesClauseEnd);

                        this.containsOnDuplicateKeyUpdate = true;
                        lookForOnDuplicateKeyUpdate = false;

                    } else if (strInspector.matchesIgnoreCase(LAST_INSERT_ID_FUNC) != -1) { // Can't rewrite as multi-values if LAST_INSERT_ID function is used.
                        rewritableAsMultiValues = false;
                        strInspector.incrementPosition(LAST_INSERT_ID_FUNC.length() - 1); // Advance to the end of "LAST_INSERT_ID" and capture last character.
                        currPos = strInspector.getPosition();
                        currChar = strInspector.getChar();
                        strInspector.incrementPosition();
                    }
                }

                if (lookForOnDuplicateKeyUpdate && currPos == strInspector.getPosition() && (matchEnd = strInspector.matchesIgnoreCase(ODKU_CLAUSE)) != -1) {
                    strInspector.incrementPosition(matchEnd - strInspector.getPosition() - 1); // Advance to the end of "ODKU" and capture last character.
                    currPos = strInspector.getPosition();
                    currChar = strInspector.getChar();
                    strInspector.incrementPosition();

                    this.containsOnDuplicateKeyUpdate = true;
                    lookForOnDuplicateKeyUpdate = false;
                }

                if (lookForLimitAndOffset) {
                    if (!matchedLimitClause && currPos == strInspector.getPosition() && (matchEnd = strInspector.matchesIgnoreCase(LIMIT_CLAUSE)) != -1) {
                        boolean leftBounded = currPos > lastPos + 1 || lastChar == ')'; // ')' would mark the ending of an expression: "... (1=1)LIMIT ...".

                        strInspector.incrementPosition(matchEnd - strInspector.getPosition() - 1); // Advance to the end of "LIMIT" and capture last character.
                        currPos = strInspector.getPosition();
                        currChar = strInspector.getChar();
                        strInspector.incrementPosition();

                        int endPos = strInspector.getPosition();
                        int nextPos = strInspector.indexOfNextChar(); // Position on the first meaningful character after LIMIT.
                        boolean rightBounded = nextPos > endPos;

                        if (leftBounded && rightBounded) { // LIMIT keyword must not be part of another string, such as a table or column name.
                            matchedLimitClause = true;
                            withinLimitClause = true;
                        }
                    } else if (withinLimitClause && currPos == strInspector.getPosition() && (matchEnd = strInspector.matchesIgnoreCase(OFFSET_CLAUSE)) != -1) {
                        boolean leftBounded = currPos > lastPos + 1;

                        strInspector.incrementPosition(matchEnd - strInspector.getPosition() - 1); // Advance to the end of "OFFSET" and capture last character.
                        currPos = strInspector.getPosition();
                        currChar = strInspector.getChar();
                        strInspector.incrementPosition();

                        int endPos = strInspector.getPosition();
                        int nextPos = strInspector.indexOfNextChar(); // Position on the first meaningful character after OFFSET.
                        boolean rightBounded = nextPos > endPos;

                        if (!leftBounded || !rightBounded) { // OFFSET keyword must not be part of another string, such as a table or column name.
                            withinLimitClause = false;
                        }
                    } else if (withinLimitClause) {
                        // If LIMIT was previously found, it's still possible to find a placeholder while digits or comma keep coming: "LIMIT [_digits_|?], ?".
                        withinLimitClause = withinLimitClause && currPos == strInspector.getPosition() && (currChar == ',' || Character.isDigit(currChar));
                    }
                }

                if (currPos == strInspector.getPosition()) {
                    strInspector.incrementPosition();
                }
            }

            lastPos = currPos;
            lastChar = currChar;
        }
        staticEndpoints.add(generalEndpointStart);
        staticEndpoints.add(this.queryLength);
        if (rewritableAsMultiValues) {
            if (!valuesClauseEndFound) {
                if (valuesClauseEnd == -1) {
                    valuesClauseEnd = this.queryLength;
                }
                valuesClauseEndFound = true;
                withinValuesClause = false;

                this.valuesEndpoints.add(valuesEndpointStart);
                this.valuesEndpoints.add(valuesClauseEnd);
            }

            if (valuesClauseBeginFound && valuesClauseEndFound) {
                this.valuesClauseLength = valuesClauseEnd - valuesClauseBegin;
            } else {
                rewritableAsMultiValues = false;
            }
        } else {
            this.valuesEndpoints.clear();
        }
        this.isRewritableWithMultiValuesClause = rewritableAsMultiValues;

        this.staticSqlParts = new byte[this.numberOfPlaceholders + 1][];
        for (int i = 0, j = 0; i <= this.numberOfPlaceholders; i++) {
            int begin = staticEndpoints.get(j++);
            int end = staticEndpoints.get(j++);
            int length = end - begin;
            this.staticSqlParts[i] = StringUtils.getBytes(this.sql, begin, length, this.encoding);
        }

        if (this.numberOfQueries > 1) {
            this.statementKeyword = MULTIPLE_QUERIES_TAG;
        }
    }

    /**
     * Constructs a {@link QueryInfo} object with a multi-value clause for the specified batch count, that stems from the specified baseQueryInfo.
     *
     * @param baseQueryInfo
     *            the {@link QueryInfo} instance that provides the query static parts used to create the new instance now augmented to accommodate the extra
     *            number of parameters
     * @param batchCount
     *            the number of batches, i.e., the number of times the VALUES clause needs to be repeated inside the new query
     */
    private QueryInfo(QueryInfo baseQueryInfo, int batchCount) {
        this.baseQueryInfo = baseQueryInfo;

        this.sql = null;
        this.encoding = this.baseQueryInfo.encoding;
        this.queryReturnType = this.baseQueryInfo.queryReturnType;
        this.queryLength = 0;
        this.queryStartPos = this.baseQueryInfo.queryStartPos;
        this.statementFirstChar = this.baseQueryInfo.statementFirstChar;
        this.statementKeyword = this.baseQueryInfo.statementKeyword;
        this.batchCount = batchCount;
        this.numberOfPlaceholders = this.baseQueryInfo.numberOfPlaceholders * this.batchCount;
        this.numberOfQueries = 1;
        this.containsOnDuplicateKeyUpdate = this.baseQueryInfo.containsOnDuplicateKeyUpdate;
        this.isRewritableWithMultiValuesClause = true;
        this.valuesClauseLength = -1;

        if (this.numberOfPlaceholders == 0) {
            this.staticSqlParts = new byte[1][];

            int begin = this.baseQueryInfo.valuesEndpoints.get(0);
            int end = this.baseQueryInfo.valuesEndpoints.get(1);
            int length = end - begin;
            byte[] valuesSegment = StringUtils.getBytes(this.baseQueryInfo.sql, begin, length, this.encoding);
            byte[] bindingSegment = StringUtils.getBytes(",", this.encoding);

            // First batch is in the query.
            ByteBuffer queryByteBuffer = ByteBuffer.allocate(this.baseQueryInfo.queryLength + (length + bindingSegment.length) * (batchCount - 1));

            // Head section: from the start to the end of the values - includes first values batch.
            queryByteBuffer.put(StringUtils.getBytes(this.baseQueryInfo.sql, 0, this.baseQueryInfo.valuesEndpoints.get(1), this.encoding));

            // Values section: repeat as many times as needed to complete the requested batch count.
            for (int i = 0; i < this.batchCount - 1; i++) {
                // Add the segment that binds two batches followed by the next batch.
                queryByteBuffer.put(bindingSegment);
                queryByteBuffer.put(valuesSegment);
            }

            // Tail section: from the end of values until the end.
            begin = this.baseQueryInfo.valuesEndpoints.get(1);
            end = this.baseQueryInfo.queryLength;
            length = end - begin;
            queryByteBuffer.put(StringUtils.getBytes(this.baseQueryInfo.sql, begin, length, this.encoding));

            this.staticSqlParts[0] = queryByteBuffer.array();

        } else {
            this.staticSqlParts = new byte[this.numberOfPlaceholders + 1][];
            this.placeholderPurposes = new ArrayList<>(this.numberOfPlaceholders);

            // Build the values binding segment: [values_end][comma][values_begin], e.g., "),(".
            int begin = this.baseQueryInfo.valuesEndpoints.get(this.baseQueryInfo.valuesEndpoints.size() - 2);
            int end = this.baseQueryInfo.valuesEndpoints.get(this.baseQueryInfo.valuesEndpoints.size() - 1);
            int length = end - begin;
            byte[] valuesEndSegment = StringUtils.getBytes(this.baseQueryInfo.sql, begin, length, this.encoding);
            byte[] delimiter = StringUtils.getBytes(",", this.encoding);
            begin = this.baseQueryInfo.valuesEndpoints.get(0);
            end = this.baseQueryInfo.valuesEndpoints.get(1);
            length = end - begin;
            byte[] valuesBeginSegment = StringUtils.getBytes(this.baseQueryInfo.sql, begin, length, this.encoding);
            ByteBuffer bindingSegmentByteBuffer = ByteBuffer.allocate(valuesEndSegment.length + delimiter.length + valuesBeginSegment.length);
            bindingSegmentByteBuffer.put(valuesEndSegment).put(delimiter).put(valuesBeginSegment);
            byte[] bindingSegment = bindingSegmentByteBuffer.array();

            // Head section: same as in the original query.
            this.staticSqlParts[0] = this.baseQueryInfo.staticSqlParts[0];

            // Values section: repeat as many times as the requested batch count.
            for (int i = 0, p = 1; i < this.batchCount; i++, p++) {
                for (int j = 1; j < this.baseQueryInfo.staticSqlParts.length - 1; j++, p++) {
                    this.staticSqlParts[p] = this.baseQueryInfo.staticSqlParts[j];
                }
                // Add the segment that binds two batches.
                this.staticSqlParts[p] = bindingSegment;
                this.placeholderPurposes.addAll(this.baseQueryInfo.placeholderPurposes);
            }

            // Tail section: same as in the original query.
            this.staticSqlParts[this.staticSqlParts.length - 1] = this.baseQueryInfo.staticSqlParts[this.baseQueryInfo.staticSqlParts.length - 1];
        }
    }

    /**
     * Returns the number of queries identified in the original SQL string. Different queries are identified by the presence of the query delimiter character,
     * i.e., a semicolon.
     *
     * @return the number of queries identified in the original SQL string
     */
    public int getNumberOfQueries() {
        return this.numberOfQueries;
    }

    /**
     * Returns the number of placeholders found in the query.
     *
     * @return the number of placeholders in the query
     */
    public int getNumberOfPlaceholders() {
        return this.numberOfPlaceholders;
    }

    /**
     * Returns the return type of the parsed query. This operation does not take into consideration the multiplicity of queries in the specified SQL.
     *
     * @return the return type of the parsed query
     */
    public QueryReturnType getQueryReturnType() {
        return this.queryReturnType;
    }

    /**
     * Returns the first character of the statement from the query used to build this {@link QueryInfo}.
     *
     * @return the first character of the statement
     */
    public char getFirstStmtChar() {
        /* TODO: First char based logic is questionable. Consider replacing by statement check. */
        return this.baseQueryInfo.statementFirstChar;
    }

    /**
     * Returns the statement keyword from the query used to build this {@link QueryInfo}.
     *
     * @return
     *         the statement keyword
     */
    public String getStatementKeyword() {
        return this.statementKeyword;
    }

    /**
     * If this object represents a query that is re-writable as a multi-values statement and if rewriting batched statements is enabled, then returns the
     * length of the parsed VALUES clause section, including the placeholder characters themselves, otherwise returns -1.
     *
     * @return the length of the parsed VALUES clause section, including the placeholder characters themselves, otherwise returns -1
     */
    public int getValuesClauseLength() {
        return this.baseQueryInfo.valuesClauseLength;
    }

    /**
     * Returns a list containing the purpose of each one of the placeholders found according to their positional ordering.
     *
     * @return the list of the placeholders purposes.
     */
    public List<PlaceholderPurpose> getPlaceholderPurposes() {
        return Collections.unmodifiableList(this.placeholderPurposes);
    }

    /**
     * Does this query info represent a query that contains an ON DUPLICATE KEY UPDATE clause? This operation does not take into consideration the multiplicity
     * of queries in the original SQL.
     *
     * Checking whether the original query contains an ON DUPLICATE KEY UPDATE clause is conditional to how the connection properties
     * 'dontCheckOnDuplicateKeyUpdateInSQL' and 'rewriteBatchedStatements' are set, with 'rewriteBatchedStatements=true' implicitly disabling
     * 'dontCheckOnDuplicateKeyUpdateInSQL'.
     *
     * @return <code>true</code> if the query or any of the original queries contain an ON DUPLICATE KEY UPDATE clause.
     */
    public boolean containsOnDuplicateKeyUpdate() {
        return this.containsOnDuplicateKeyUpdate;
    }

    /**
     * Returns the static sections of the parsed query, as byte arrays, split by the places where the placeholders were located.
     *
     * @return the static sections of the parsed query, as byte arrays, split by the places where the placeholders were located
     */
    public byte[][] getStaticSqlParts() {
        return this.staticSqlParts;
    }

    /**
     * Can this query be rewritten as a multi-values clause?
     *
     * @return <code>true</code> if the query can be rewritten as a multi-values query.
     */
    public boolean isRewritableWithMultiValuesClause() {
        return this.isRewritableWithMultiValuesClause;
    }

    /**
     * Returns a {@link QueryInfo} for a multi-values INSERT/REPLACE assembled for the specified batch count, without re-parsing.
     *
     * @param count
     *            the number of parameter batches
     * @return {@link QueryInfo}
     */
    public QueryInfo getQueryInfoForBatch(int count) {
        if (count == 1) {
            return this.baseQueryInfo;
        }
        if (count == this.batchCount) {
            return this;
        }

        if (!this.isRewritableWithMultiValuesClause) {
            return null;
        }

        return new QueryInfo(this.baseQueryInfo, count);
    }

    /**
     * Returns a preparable query for the batch count of this {@link QueryInfo}.
     *
     * @return
     *         a preparable query string with the appropriate number of placeholders
     */
    public String getSqlForBatch() {
        if (this.batchCount == 1) {
            return this.baseQueryInfo.sql;
        }

        int size = this.baseQueryInfo.queryLength + (this.batchCount - 1) * this.baseQueryInfo.valuesClauseLength + this.batchCount - 1;
        StringBuilder buf = new StringBuilder(size);
        buf.append(StringUtils.toString(this.staticSqlParts[0], this.encoding));
        for (int i = 1; i < this.staticSqlParts.length; i++) {
            buf.append("?").append(StringUtils.toString(this.staticSqlParts[i], this.encoding));
        }
        return buf.toString();
    }

    /**
     * Returns a preparable query for the specified batch count.
     *
     * @param count
     *            number of parameter batches
     * @return a preparable query string with the appropriate number of placeholders
     */
    public String getSqlForBatch(int count) {
        QueryInfo batchInfo = getQueryInfoForBatch(count);
        return batchInfo.getSqlForBatch();
    }

    /**
     * Finds and returns the position of the first non-whitespace character from the specified SQL, skipping comments and quoted text.
     *
     * @param sql
     *            the query to search
     * @param noBackslashEscapes
     *            whether backslash escapes are disabled or not
     * @return the position of the first character of the query
     */
    public static int indexOfStatementKeyword(String sql, boolean noBackslashEscapes) {
        return StringUtils.indexOfNextAlphanumericChar(0, sql, OPENING_MARKERS, CLOSING_MARKERS, OVERRIDING_MARKERS,
                noBackslashEscapes ? SearchMode.__MRK_COM_MYM_HNT_WS : SearchMode.__BSE_MRK_COM_MYM_HNT_WS);
    }

    /**
     * Finds and returns the first non-whitespace character from the specified SQL, skipping comments and quoted text.
     *
     * @param sql
     *            the query to search
     * @param noBackslashEscapes
     *            whether backslash escapes are disabled or not
     * @return the first character of the query, in upper case
     */
    public static char firstCharOfStatementUc(String sql, boolean noBackslashEscapes) {
        int statementKeywordPos = indexOfStatementKeyword(sql, noBackslashEscapes);
        if (statementKeywordPos == -1) {
            return Character.MIN_VALUE;
        }
        return Character.toUpperCase(sql.charAt(statementKeywordPos));
    }

    /**
     * Finds and returns the statement keyword from the specified SQL, skipping comments and quoted text.
     *
     * @param sql
     *            the query to search
     * @param noBackslashEscapes
     *            whether backslash escapes are disabled or not
     * @return the statement keyword of the query
     */
    public static String getStatementKeyword(String sql, boolean noBackslashEscapes) {
        StringInspector strInspector = new StringInspector(sql, 0, OPENING_MARKERS, CLOSING_MARKERS, OVERRIDING_MARKERS,
                noBackslashEscapes ? SearchMode.__MRK_COM_MYM_HNT_WS : SearchMode.__BSE_MRK_COM_MYM_HNT_WS);
        int begin = strInspector.indexOfNextAlphanumericChar();
        if (begin == -1) {
            return "";
        }
        int end = 0;
        int nextChar = begin;
        do {
            end = nextChar + 1;
            strInspector.incrementPosition();
            nextChar = strInspector.indexOfNextChar();
        } while (nextChar == end);
        return sql.substring(begin, end).toUpperCase();
    }

    /**
     * Checks whether the given query is safe to run in a read-only session. In case of doubt it is assumed to be safe. This operation does not take into
     * consideration the multiplicity of queries in the specified SQL.
     *
     * @param sql
     *            the query to check
     * @param noBackslashEscapes
     *            whether backslash escapes are disabled or not
     * @return <code>true</code> if the query is read-only safe, <code>false</code> otherwise.
     */
    public static boolean isReadOnlySafeQuery(String sql, boolean noBackslashEscapes) {
        /*
         * Read-only unsafe statements:
         * - ALTER; CHANGE; CREATE; DELETE; DROP; GRANT; IMPORT; INSERT; INSTALL; LOAD; OPTIMIZE; RENAME; REPAIR; REPLACE; RESET; REVOKE; TRUNCATE; UNINSTALL;
         * - UPDATE; WITH ... DELETE|UPDATE
         *
         * Read-only safe statements:
         * - ANALYZE; BEGIN; BINLOG; CACHE; CALL; CHECK; CHECKSUM; CLONE; COMMIT; DEALLOCATE; DESC; DESCRIBE; EXECUTE; EXPLAIN; FLUSH; GET; HANDLER; HELP; KILL;
         * - LOCK; PREPARE; PURGE; RELEASE; RESIGNAL; ROLLBACK; SAVEPOINT; SELECT; SET; SHOW; SIGNAL; START; STOP; TABLE; UNLOCK; USE; VALUES;
         * - WITH ... [SELECT|TABLE|VALUES]; XA
         */
        int statementKeywordPos = indexOfStatementKeyword(sql, noBackslashEscapes);
        if (statementKeywordPos == -1) {
            return true; // Assume it's safe.
        }
        char firstStatementChar = Character.toUpperCase(sql.charAt(statementKeywordPos));
        if (firstStatementChar == 'A' && StringUtils.startsWithIgnoreCaseAndWs(sql, "ALTER", statementKeywordPos)) {
            return false;
        } else if (firstStatementChar == 'C' && (StringUtils.startsWithIgnoreCaseAndWs(sql, "CHANGE", statementKeywordPos)
                || StringUtils.startsWithIgnoreCaseAndWs(sql, "CREATE", statementKeywordPos))) {
            return false;
        } else if (firstStatementChar == 'D' && (StringUtils.startsWithIgnoreCaseAndWs(sql, "DELETE", statementKeywordPos)
                || StringUtils.startsWithIgnoreCaseAndWs(sql, "DROP", statementKeywordPos))) {
            return false;
        } else if (firstStatementChar == 'G' && StringUtils.startsWithIgnoreCaseAndWs(sql, "GRANT", statementKeywordPos)) {
            return false;
        } else if (firstStatementChar == 'I' && (StringUtils.startsWithIgnoreCaseAndWs(sql, "IMPORT", statementKeywordPos)
                || StringUtils.startsWithIgnoreCaseAndWs(sql, "INSERT", statementKeywordPos)
                || StringUtils.startsWithIgnoreCaseAndWs(sql, "INSTALL", statementKeywordPos))) {
            return false;
        } else if (firstStatementChar == 'L' && StringUtils.startsWithIgnoreCaseAndWs(sql, "LOAD", statementKeywordPos)) {
            return false;
        } else if (firstStatementChar == 'O' && StringUtils.startsWithIgnoreCaseAndWs(sql, "OPTIMIZE", statementKeywordPos)) {
            return false;
        } else if (firstStatementChar == 'R' && (StringUtils.startsWithIgnoreCaseAndWs(sql, "RENAME", statementKeywordPos)
                || StringUtils.startsWithIgnoreCaseAndWs(sql, "REPAIR", statementKeywordPos)
                || StringUtils.startsWithIgnoreCaseAndWs(sql, "REPLACE", statementKeywordPos)
                || StringUtils.startsWithIgnoreCaseAndWs(sql, "RESET", statementKeywordPos)
                || StringUtils.startsWithIgnoreCaseAndWs(sql, "REVOKE", statementKeywordPos))) {
            return false;
        } else if (firstStatementChar == 'T' && StringUtils.startsWithIgnoreCaseAndWs(sql, "TRUNCATE", statementKeywordPos)) {
            return false;
        } else if (firstStatementChar == 'U' && (StringUtils.startsWithIgnoreCaseAndWs(sql, "UNINSTALL", statementKeywordPos)
                || StringUtils.startsWithIgnoreCaseAndWs(sql, "UPDATE", statementKeywordPos))) {
            return false;
        } else if (firstStatementChar == 'W' && StringUtils.startsWithIgnoreCaseAndWs(sql, "WITH", statementKeywordPos)) {
            String context = getContextForWithStatement(sql, noBackslashEscapes);
            return context == null || !context.equalsIgnoreCase("DELETE") && !context.equalsIgnoreCase("UPDATE");
        }
        return true; // Assume it's safe by default.
    }

    /**
     * Returns the type of return that can be expected from executing the given query. This operation does not take into consideration the multiplicity
     * of queries in the specified SQL.
     *
     * @param sql
     *            the query to check
     * @param noBackslashEscapes
     *            whether backslash escapes are disabled or not
     * @return the return type that can be expected from the given query, one of the elements of {@link QueryReturnType}.
     */
    public static QueryReturnType getQueryReturnType(String sql, boolean noBackslashEscapes) {
        /*
         * Statements that return results:
         * - ANALYZE; CHECK/CHECKSUM; DESC/DESCRIBE; EXPLAIN; HELP; OPTIMIZE; REPAIR; SELECT; SHOW; TABLE; VALUES; WITH ... SELECT|TABLE|VALUES ...; XA RECOVER;
         *
         * Statements that may return results:
         * - CALL; EXECUTE;
         *
         * Statements that do not return results:
         * - ALTER; BINLOG; CACHE; CHANGE; CLONE; COMMIT; CREATE; DEALLOCATE; DELETE; DO; DROP; FLUSH; GET; GRANT; HANDLER; IMPORT; INSERT; INSTALL; KILL; LOAD;
         * - LOCK; PREPARE; PURGE; RELEASE; RENAME; REPLACE; RESET; RESIGNAL; RESTART; REVOKE; ROLLBACK; SAVEPOINT; SET; SHUTDOWN; SIGNAL; START; STOP;
         * - TRUNCATE; UNINSTALL; UNLOCK; UPDATE; USE; WITH ... DELETE|UPDATE ...; XA [!RECOVER];
         */
        int statementKeywordPos = indexOfStatementKeyword(sql, noBackslashEscapes);
        if (statementKeywordPos == -1) {
            return QueryReturnType.NONE;
        }
        char firstStatementChar = Character.toUpperCase(sql.charAt(statementKeywordPos));
        if (firstStatementChar == 'A' && StringUtils.startsWithIgnoreCaseAndWs(sql, "ANALYZE", statementKeywordPos)) {
            return QueryReturnType.PRODUCES_RESULT_SET;
        } else if (firstStatementChar == 'C' && StringUtils.startsWithIgnoreCaseAndWs(sql, "CALL", statementKeywordPos)) {
            return QueryReturnType.MAY_PRODUCE_RESULT_SET;
        } else if (firstStatementChar == 'C' && StringUtils.startsWithIgnoreCaseAndWs(sql, "CHECK", statementKeywordPos)) { // Also matches "CHECKSUM".
            return QueryReturnType.PRODUCES_RESULT_SET;
        } else if (firstStatementChar == 'D' && StringUtils.startsWithIgnoreCaseAndWs(sql, "DESC", statementKeywordPos)) { // Also matches "DESCRIBE".
            return QueryReturnType.PRODUCES_RESULT_SET;
        } else if (firstStatementChar == 'E' && StringUtils.startsWithIgnoreCaseAndWs(sql, "EXPLAIN", statementKeywordPos)) {
            return QueryReturnType.PRODUCES_RESULT_SET;
        } else if (firstStatementChar == 'E' && StringUtils.startsWithIgnoreCaseAndWs(sql, "EXECUTE", statementKeywordPos)) {
            return QueryReturnType.MAY_PRODUCE_RESULT_SET;
        } else if (firstStatementChar == 'H' && StringUtils.startsWithIgnoreCaseAndWs(sql, "HELP", statementKeywordPos)) {
            return QueryReturnType.PRODUCES_RESULT_SET;
        } else if (firstStatementChar == 'O' && StringUtils.startsWithIgnoreCaseAndWs(sql, "OPTIMIZE", statementKeywordPos)) {
            return QueryReturnType.PRODUCES_RESULT_SET;
        } else if (firstStatementChar == 'R' && StringUtils.startsWithIgnoreCaseAndWs(sql, "REPAIR", statementKeywordPos)) {
            return QueryReturnType.PRODUCES_RESULT_SET;
        } else if (firstStatementChar == 'S' && (StringUtils.startsWithIgnoreCaseAndWs(sql, "SELECT", statementKeywordPos)
                || StringUtils.startsWithIgnoreCaseAndWs(sql, "SHOW", statementKeywordPos))) {
            return QueryReturnType.PRODUCES_RESULT_SET;
        } else if (firstStatementChar == 'T' && StringUtils.startsWithIgnoreCaseAndWs(sql, "TABLE", statementKeywordPos)) {
            return QueryReturnType.PRODUCES_RESULT_SET;
        } else if (firstStatementChar == 'V' && StringUtils.startsWithIgnoreCaseAndWs(sql, "VALUES", statementKeywordPos)) {
            return QueryReturnType.PRODUCES_RESULT_SET;
        } else if (firstStatementChar == 'W' && StringUtils.startsWithIgnoreCaseAndWs(sql, "WITH", statementKeywordPos)) {
            String context = getContextForWithStatement(sql, noBackslashEscapes);
            if ("SELECT".equalsIgnoreCase(context) || "TABLE".equalsIgnoreCase(context) || "VALUES".equalsIgnoreCase(context)) {
                return QueryReturnType.PRODUCES_RESULT_SET;
            } else if ("UPDATE".equalsIgnoreCase(context) || "DELETE".equalsIgnoreCase(context)) {
                return QueryReturnType.DOES_NOT_PRODUCE_RESULT_SET;
            } else {
                return QueryReturnType.MAY_PRODUCE_RESULT_SET;
            }
        } else if (firstStatementChar == 'X' && StringUtils.indexOfIgnoreCase(statementKeywordPos, sql, new String[] { "XA", "RECOVER" }, OPENING_MARKERS,
                CLOSING_MARKERS, noBackslashEscapes ? SearchMode.__MRK_COM_MYM_HNT_WS : SearchMode.__FULL) == statementKeywordPos) {
            return QueryReturnType.PRODUCES_RESULT_SET;
        }
        return QueryReturnType.DOES_NOT_PRODUCE_RESULT_SET;
    }

    /**
     * Returns the context of the WITH statement. The context can be: SELECT, TABLE, VALUES, UPDATE or DELETE. This operation does not take into consideration
     * the multiplicity of queries in the specified SQL.
     *
     * @param sql
     *            the query to search
     * @param noBackslashEscapes
     *            whether backslash escapes are disabled or not
     * @return the context of the WITH statement or null if failed to find it
     */
    private static String getContextForWithStatement(String sql, boolean noBackslashEscapes) {
        // Must remove all comments first.
        String commentsFreeSql = StringUtils.stripCommentsAndHints(sql, OPENING_MARKERS, CLOSING_MARKERS, !noBackslashEscapes);

        // Iterate through statement words, skipping all sub-queries sections enclosed by parens.
        StringInspector strInspector = new StringInspector(commentsFreeSql, OPENING_MARKERS + "(", CLOSING_MARKERS + ")", OPENING_MARKERS,
                noBackslashEscapes ? SearchMode.__MRK_COM_MYM_HNT_WS : SearchMode.__BSE_MRK_COM_MYM_HNT_WS);
        boolean asFound = false;
        while (true) {
            int nws = strInspector.indexOfNextNonWsChar();
            if (nws == -1) { // No more parts to analyze.
                return null;
            }
            int ws = strInspector.indexOfNextWsChar();
            if (ws == -1) { // End of query.
                ws = commentsFreeSql.length();
            }
            String section = commentsFreeSql.substring(nws, ws);
            if (!asFound && section.equalsIgnoreCase(AS_CLAUSE)) {
                asFound = true; // Since the subquery part is skipped, this must be followed by a "," or the context statement.
            } else if (asFound) {
                if (section.charAt(0) == ',') {
                    asFound = false; // Another CTE is expected.
                } else {
                    return section;
                }
            }
        }
    }

    /**
     * Checks whether the specified SQL contains or not an ON DUPLICATE KEY UPDATE clause. This operation does not take into consideration the multiplicity of
     * queries in the specified SQL.
     *
     * @param sql
     *            the query to search
     * @param noBackslashEscapes
     *            whether backslash escapes are disabled or not.
     * @return <code>true</code> if the query contains an ON DUPLICATE KEY UPDATE clause, <code>false</code> otherwise
     */
    public static boolean containsOnDuplicateKeyUpdateClause(String sql, boolean noBackslashEscapes) {
        return StringUtils.indexOfIgnoreCase(0, sql, ODKU_CLAUSE, OPENING_MARKERS, CLOSING_MARKERS,
                noBackslashEscapes ? SearchMode.__MRK_COM_MYM_HNT_WS : SearchMode.__BSE_MRK_COM_MYM_HNT_WS) != -1;
    }

}
