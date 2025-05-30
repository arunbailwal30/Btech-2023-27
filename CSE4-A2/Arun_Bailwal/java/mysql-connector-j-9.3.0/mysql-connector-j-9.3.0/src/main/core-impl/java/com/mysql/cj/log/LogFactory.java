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

package com.mysql.cj.log;

import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.util.Util;

/**
 * Creates instances of loggers for the driver to use.
 */
public class LogFactory {

    /**
     * Returns a logger instance of the given class, with the given instance
     * name.
     *
     * @param className
     *            the class to instantiate
     * @param instanceName
     *            the instance name
     * @return a logger instance
     */
    public static Log getLogger(String className, String instanceName) {
        if (className == null) {
            throw ExceptionFactory.createException(WrongArgumentException.class, "Logger class can not be NULL");
        }

        if (instanceName == null) {
            throw ExceptionFactory.createException(WrongArgumentException.class, "Logger instance name can not be NULL");
        }

        try {
            return Util.getInstance(Log.class, className, new Class<?>[] { String.class }, new Object[] { instanceName }, null);
        } catch (CJException e1) {
            if (ClassNotFoundException.class.isInstance(e1.getCause())) {
                // Retry with package name prepended.
                try {
                    return Util.getInstance(Log.class, Util.getPackageName(LogFactory.class) + "." + className, new Class<?>[] { String.class },
                            new Object[] { instanceName }, null);
                } catch (CJException e2) {
                    throw e1;
                }
            }
            throw e1;
        }
    }

}
