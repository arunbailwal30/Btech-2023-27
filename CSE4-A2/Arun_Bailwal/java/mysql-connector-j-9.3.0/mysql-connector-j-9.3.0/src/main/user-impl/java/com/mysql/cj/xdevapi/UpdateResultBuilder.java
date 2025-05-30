/*
 * Copyright (c) 2019, 2025, Oracle and/or its affiliates.
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

import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.protocol.ProtocolEntity;
import com.mysql.cj.protocol.ResultBuilder;
import com.mysql.cj.protocol.x.Notice;
import com.mysql.cj.protocol.x.Ok;
import com.mysql.cj.protocol.x.StatementExecuteOk;
import com.mysql.cj.protocol.x.StatementExecuteOkBuilder;

/**
 * Result builder producing an {@link UpdateResult} instance.
 */
public class UpdateResultBuilder<T extends Result> implements ResultBuilder<T> {

    protected StatementExecuteOkBuilder statementExecuteOkBuilder = new StatementExecuteOkBuilder();

    @Override
    public boolean addProtocolEntity(ProtocolEntity entity) {
        if (entity instanceof Notice) {
            this.statementExecuteOkBuilder.addProtocolEntity(entity);
            return false;

        } else if (entity instanceof StatementExecuteOk) {
            return true;

        } else if (entity instanceof Ok) {
            return true;
        }
        throw ExceptionFactory.createException(WrongArgumentException.class, "Unexpected protocol entity " + entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T build() {
        return (T) new UpdateResult(this.statementExecuteOkBuilder.build());
    }

}
