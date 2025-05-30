/*
 * Copyright (c) 2016, 2025, Oracle and/or its affiliates.
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

package com.mysql.cj.xdevapi;

/**
 * Represents the item of XPLUGIN_STMT_LIST_OBJECTS operation result.
 */
public class DatabaseObjectDescription {

    private String objectName;
    private DatabaseObject.DbObjectType objectType;

    /**
     * Constructor.
     *
     * @param name
     *            database object name
     * @param type
     *            database object type, one of COLLECTION, TABLE, VIEW or COLLECTION_VIEW
     */
    public DatabaseObjectDescription(String name, String type) {
        this.objectName = name;
        this.objectType = DatabaseObject.DbObjectType.valueOf(type);
    }

    /**
     * Get database object name.
     *
     * @return database object name
     */
    public String getObjectName() {
        return this.objectName;
    }

    /**
     * Get database object type
     *
     * @return {@link DatabaseObject.DbObjectType}
     */
    public DatabaseObject.DbObjectType getObjectType() {
        return this.objectType;
    }

}
