/*
 * Copyright (c) 2015, 2025, Oracle and/or its affiliates.
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

package com.mysql.cj.conf;

import java.util.Arrays;

import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.util.StringUtils;

public class BooleanPropertyDefinition extends AbstractPropertyDefinition<Boolean> {

    private static final long serialVersionUID = -7288366734350231540L;

    public enum AllowableValues {

        TRUE(true), FALSE(false), YES(true), NO(false);

        private boolean asBoolean;

        private AllowableValues(boolean booleanValue) {
            this.asBoolean = booleanValue;
        }

        public boolean asBoolean() {
            return this.asBoolean;
        }

    }

    public BooleanPropertyDefinition(PropertyKey key, Boolean defaultValue, boolean isRuntimeModifiable, String description, String sinceVersion,
            String category, int orderInCategory) {
        super(key, defaultValue, isRuntimeModifiable, description, sinceVersion, category, orderInCategory);
    }

    @Override
    public String[] getAllowableValues() {
        return getBooleanAllowableValues();
    }

    @Override
    public Boolean parseObject(String value, ExceptionInterceptor exceptionInterceptor) {
        return booleanFrom(getName(), value, exceptionInterceptor);
    }

    /**
     * Creates instance of BooleanProperty.
     *
     * @return RuntimeProperty
     */
    @Override
    public RuntimeProperty<Boolean> createRuntimeProperty() {
        return new BooleanProperty(this);
    }

    public static Boolean booleanFrom(String name, String value, ExceptionInterceptor exceptionInterceptor) {
        try {
            return AllowableValues.valueOf(value.toUpperCase()).asBoolean();
        } catch (Exception e) {
            throw ExceptionFactory.createException(
                    Messages.getString("PropertyDefinition.1",
                            new Object[] { name, StringUtils.stringArrayToString(getBooleanAllowableValues(), "'", "', '", "' or '", "'"), value }),
                    e, exceptionInterceptor);
        }
    }

    public static String[] getBooleanAllowableValues() {
        return Arrays.stream(AllowableValues.values()).map(AllowableValues::toString).toArray(String[]::new);
    }

}
