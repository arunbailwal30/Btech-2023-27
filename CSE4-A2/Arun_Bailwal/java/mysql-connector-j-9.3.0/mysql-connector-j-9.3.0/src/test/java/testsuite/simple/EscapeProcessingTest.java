/*
 * Copyright (c) 2002, 2025, Oracle and/or its affiliates.
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

package testsuite.simple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Properties;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;

import com.mysql.cj.MysqlConnection;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.util.TimeUtil;

import testsuite.BaseTestCase;

/**
 * Tests escape processing
 */
public class EscapeProcessingTest extends BaseTestCase {

    /**
     * Tests the escape processing functionality
     *
     * @throws Exception
     */
    @Test
    public void testEscapeProcessing() throws Exception {
        TimeZone sessionTz = ((MysqlConnection) this.conn).getSession().getServerSession().getSessionTimeZone();
        ZonedDateTime zdt = LocalDateTime.parse("1997-05-24T10:30:29.123").atZone(ZoneId.systemDefault()).withZoneSameInstant(sessionTz.toZoneId());
        boolean withFract = versionMeetsMinimum(5, 6, 4);

        String results = "select dayname (abs(now())),   -- Today    \n" //
                + "           '1997-05-24',  -- a date                    \n" + "           '10:30:29',  -- a time                     \n" + "           '"
                + zdt.format(withFract ? TimeUtil.DATETIME_FORMATTER_WITH_MILLIS_NO_OFFSET : TimeUtil.DATETIME_FORMATTER_NO_FRACT_NO_OFFSET)
                + "', -- a timestamp  \n" + "          '{string data with { or } will not be altered'   \n"
                + "--  Also note that you can safely include { and } in comments";

        String exSql = "select {fn dayname ({fn abs({fn now()})})},   -- Today    \n" //
                + "           {d '1997-05-24'},  -- a date                    \n" + "           {t '10:30:29' },  -- a time                     \n"
                + "           {ts '1997-05-24 10:30:29.123'}, -- a timestamp  \n" + "          '{string data with { or } will not be altered'   \n"
                + "--  Also note that you can safely include { and } in comments";

        String escapedSql = this.conn.nativeSQL(exSql);

        assertTrue(results.equals(escapedSql), escapedSql + "\n\nwas expected to match\n\n" + results);
    }

    /**
     * JDBC-4.0 spec will allow either SQL_ or not for type in {fn convert ...}
     *
     * @throws Exception
     */
    @Test
    public void testConvertEscape() throws Exception {
        assertEquals(this.conn.nativeSQL("{fn convert(abcd, SQL_INTEGER)}"), this.conn.nativeSQL("{fn convert(abcd, INTEGER)}"));
    }

    /**
     * Tests that the escape tokenizer converts timestamp values
     * wrt. timezones
     *
     * @throws Exception
     */
    @Test
    public void testTimestampConversion() throws Exception {
        TimeZone currentTimeZone = TimeZone.getDefault();
        String[] availableIds = TimeZone.getAvailableIDs(currentTimeZone.getRawOffset() - 3600 * 1000 * 7);
        String newTimeZone = null;

        if (availableIds.length > 0) {
            for (String id : availableIds) {
                try {
                    ZoneId.of(id);
                    newTimeZone = id;
                } catch (Exception e) {
                    // ignore, try different time zone ID
                }
            }
        }

        if (newTimeZone == null) {
            newTimeZone = "UTC"; // punt
        }

        Properties props = new Properties();
        props.setProperty(PropertyKey.sslMode.getKeyName(), "DISABLED");
        props.setProperty(PropertyKey.allowPublicKeyRetrieval.getKeyName(), "true");

        props.setProperty(PropertyKey.connectionTimeZone.getKeyName(), newTimeZone);
        Connection tzConn = null;

        try {
            String escapeToken = "SELECT {ts '2002-11-12 10:00:00'} {t '05:11:02'}";
            tzConn = getConnectionWithProps(props);
            assertTrue(!tzConn.nativeSQL(escapeToken).equals(this.conn.nativeSQL(escapeToken)));
        } finally {
            if (tzConn != null) {
                tzConn.close();
            }
        }
    }

    /**
     * Tests fix for BUG#51313 - Escape processing is confused by multiple backslashes.
     *
     * @throws Exception
     */
    @Test
    public void testBug51313() throws Exception {
        this.stmt = this.conn.createStatement();

        this.rs = this.stmt.executeQuery("SELECT {fn lcase('My{fn UCASE(sql)}} -- DATABASE')}, {fn ucase({fn lcase('SERVER')})}"
                + " -- {escape } processing test\n -- this {fn ucase('comment') is in line 2\r\n"
                + " -- this in line 3, and previous escape sequence was malformed\n");
        assertTrue(this.rs.next());
        assertEquals("my{fn ucase(sql)}} -- database", this.rs.getString(1));
        assertEquals("SERVER", this.rs.getString(2));
        this.rs.close();

        this.rs = this.stmt.executeQuery("SELECT 'MySQL \\\\\\' testing {long \\\\\\' escape -- { \\\\\\' sequences \\\\\\' } } with escape processing '");
        assertTrue(this.rs.next());
        assertEquals("MySQL \\\' testing {long \\\' escape -- { \\\' sequences \\\' } } with escape processing ", this.rs.getString(1));
        this.rs.close();

        this.rs = this.stmt.executeQuery("SELECT 'MySQL \\'', '{ testing doubled -- } ''\\\\\\''' quotes '");
        assertTrue(this.rs.next());
        assertEquals("MySQL \'", this.rs.getString(1));
        assertEquals("{ testing doubled -- } '\\\'' quotes ", this.rs.getString(2));
        this.rs.close();

        this.rs = this.stmt.executeQuery("SELECT 'MySQL \\\\\\'''', '{ testing doubled -- } ''\\''' quotes '");
        assertTrue(this.rs.next());
        assertEquals("MySQL \\\''", this.rs.getString(1));
        assertEquals("{ testing doubled -- } '\'' quotes ", this.rs.getString(2));
        this.rs.close();
    }

}
