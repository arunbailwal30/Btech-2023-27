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

package com.mysql.cj.protocol.a;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.DataReadException;
import com.mysql.cj.protocol.InternalDate;
import com.mysql.cj.protocol.InternalTime;
import com.mysql.cj.protocol.InternalTimestamp;
import com.mysql.cj.protocol.ValueDecoder;
import com.mysql.cj.result.Field;
import com.mysql.cj.result.ValueFactory;
import com.mysql.cj.util.StringUtils;

/**
 * A {@link com.mysql.cj.protocol.ValueDecoder} for the MySQL binary (prepared statement) protocol.
 */
public class MysqlBinaryValueDecoder implements ValueDecoder {

    @Override
    public <T> T decodeTimestamp(byte[] bytes, int offset, int length, int scale, ValueFactory<T> vf) {
        if (length == 0) {
            return vf.createFromTimestamp(new InternalTimestamp());
        } else if (length != NativeConstants.BIN_LEN_DATE && length != NativeConstants.BIN_LEN_TIMESTAMP_WITH_MICROS
                && length != NativeConstants.BIN_LEN_TIMESTAMP_NO_FRAC) {
            // the value can be any of these lengths (check protocol docs)
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "TIMESTAMP" }));
        }

        int year = 0;
        int month = 0;
        int day = 0;

        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        int nanos = 0;

        year = bytes[offset + 0] & 0xff | (bytes[offset + 1] & 0xff) << 8;
        month = bytes[offset + 2];
        day = bytes[offset + 3];

        if (length > NativeConstants.BIN_LEN_DATE) {
            hours = bytes[offset + 4];
            minutes = bytes[offset + 5];
            seconds = bytes[offset + 6];
        }

        if (length > NativeConstants.BIN_LEN_TIMESTAMP_NO_FRAC) {
            // MySQL PS protocol uses microseconds
            nanos = 1000 * (bytes[offset + 7] & 0xff | (bytes[offset + 8] & 0xff) << 8 | (bytes[offset + 9] & 0xff) << 16 | (bytes[offset + 10] & 0xff) << 24);
        }

        return vf.createFromTimestamp(new InternalTimestamp(year, month, day, hours, minutes, seconds, nanos, scale));
    }

    @Override
    public <T> T decodeDatetime(byte[] bytes, int offset, int length, int scale, ValueFactory<T> vf) {
        if (length == 0) {
            return vf.createFromTimestamp(new InternalTimestamp());
        } else if (length != NativeConstants.BIN_LEN_DATE && length != NativeConstants.BIN_LEN_TIMESTAMP_WITH_MICROS
                && length != NativeConstants.BIN_LEN_TIMESTAMP_NO_FRAC) {
            // the value can be any of these lengths (check protocol docs)
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "TIMESTAMP" }));
        }

        int year = 0;
        int month = 0;
        int day = 0;

        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        int nanos = 0;

        year = bytes[offset + 0] & 0xff | (bytes[offset + 1] & 0xff) << 8;
        month = bytes[offset + 2];
        day = bytes[offset + 3];

        if (length > NativeConstants.BIN_LEN_DATE) {
            hours = bytes[offset + 4];
            minutes = bytes[offset + 5];
            seconds = bytes[offset + 6];
        }

        if (length > NativeConstants.BIN_LEN_TIMESTAMP_NO_FRAC) {
            // MySQL PS protocol uses microseconds
            nanos = 1000 * (bytes[offset + 7] & 0xff | (bytes[offset + 8] & 0xff) << 8 | (bytes[offset + 9] & 0xff) << 16 | (bytes[offset + 10] & 0xff) << 24);
        }

        return vf.createFromDatetime(new InternalTimestamp(year, month, day, hours, minutes, seconds, nanos, scale));
    }

    @Override
    public <T> T decodeTime(byte[] bytes, int offset, int length, int scale, ValueFactory<T> vf) {
        if (length == 0) {
            return vf.createFromTime(new InternalTime());
        } else if (length != NativeConstants.BIN_LEN_TIME_WITH_MICROS && length != NativeConstants.BIN_LEN_TIME_NO_FRAC) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "TIME" }));
        }

        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        int nanos = 0;

        boolean negative = bytes[offset] == 1;

        days = bytes[offset + 1] & 0xff | (bytes[offset + 2] & 0xff) << 8 | (bytes[offset + 3] & 0xff) << 16 | (bytes[offset + 4] & 0xff) << 24;
        hours = bytes[offset + 5];
        minutes = bytes[offset + 6];
        seconds = bytes[offset + 7];

        if (negative) {
            days *= -1;
        }

        if (length > NativeConstants.BIN_LEN_TIME_NO_FRAC) {
            // MySQL PS protocol uses microseconds
            nanos = 1000 * (bytes[offset + 8] & 0xff | (bytes[offset + 9] & 0xff) << 8 | (bytes[offset + 10] & 0xff) << 16 | (bytes[offset + 11] & 0xff) << 24);
        }

        return vf.createFromTime(new InternalTime(days * 24 + hours, minutes, seconds, nanos, scale));
    }

    @Override
    public <T> T decodeDate(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
        if (length == 0) {
            return vf.createFromDate(new InternalDate());
        } else if (length != NativeConstants.BIN_LEN_DATE) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "DATE" }));
        }
        int year = bytes[offset] & 0xff | (bytes[offset + 1] & 0xff) << 8;
        int month = bytes[offset + 2];
        int day = bytes[offset + 3];
        return vf.createFromDate(new InternalDate(year, month, day));
    }

    @Override
    public <T> T decodeUInt1(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
        if (length != NativeConstants.BIN_LEN_INT1) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "BYTE" }));
        }
        return vf.createFromLong(bytes[offset] & 0xff);
    }

    @Override
    public <T> T decodeInt1(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
        if (length != NativeConstants.BIN_LEN_INT1) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "BYTE" }));
        }
        return vf.createFromLong(bytes[offset]);
    }

    @Override
    public <T> T decodeUInt2(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
        if (length != NativeConstants.BIN_LEN_INT2) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "SHORT" }));
        }
        int asInt = bytes[offset] & 0xff | (bytes[offset + 1] & 0xff) << 8;
        return vf.createFromLong(asInt);
    }

    @Override
    public <T> T decodeInt2(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
        if (length != NativeConstants.BIN_LEN_INT2) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "SHORT" }));
        }
        short asShort = (short) (bytes[offset] & 0xff | (bytes[offset + 1] & 0xff) << 8);
        return vf.createFromLong(asShort);
    }

    @Override
    public <T> T decodeUInt4(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
        if (length != NativeConstants.BIN_LEN_INT4) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "INT" }));
        }
        long asLong = bytes[offset] & 0xff | (bytes[offset + 1] & 0xff) << 8 | (bytes[offset + 2] & 0xff) << 16 | (long) (bytes[offset + 3] & 0xff) << 24;
        return vf.createFromLong(asLong);
    }

    @Override
    public <T> T decodeInt4(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
        if (length != NativeConstants.BIN_LEN_INT4) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "SHORT" }));
        }
        int asInt = bytes[offset] & 0xff | (bytes[offset + 1] & 0xff) << 8 | (bytes[offset + 2] & 0xff) << 16 | (bytes[offset + 3] & 0xff) << 24;
        return vf.createFromLong(asInt);
    }

    @Override
    public <T> T decodeInt8(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
        if (length != NativeConstants.BIN_LEN_INT8) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "LONG" }));
        }
        long asLong = bytes[offset] & 0xff | (long) (bytes[offset + 1] & 0xff) << 8 | (long) (bytes[offset + 2] & 0xff) << 16
                | (long) (bytes[offset + 3] & 0xff) << 24 | (long) (bytes[offset + 4] & 0xff) << 32 | (long) (bytes[offset + 5] & 0xff) << 40
                | (long) (bytes[offset + 6] & 0xff) << 48 | (long) (bytes[offset + 7] & 0xff) << 56;
        return vf.createFromLong(asLong);
    }

    @Override
    public <T> T decodeUInt8(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
        if (length != NativeConstants.BIN_LEN_INT8) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "LONG" }));
        }

        // don't use BigInteger unless sign bit is used
        if ((bytes[offset + 7] & 0x80) == 0) {
            return this.decodeInt8(bytes, offset, length, vf);
        }

        // first byte is 0 to indicate sign
        byte[] bigEndian = new byte[] { 0, bytes[offset + 7], bytes[offset + 6], bytes[offset + 5], bytes[offset + 4], bytes[offset + 3], bytes[offset + 2],
                bytes[offset + 1], bytes[offset] };
        BigInteger bigInt = new BigInteger(bigEndian);
        return vf.createFromBigInteger(bigInt);
    }

    @Override
    public <T> T decodeFloat(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
        if (length != NativeConstants.BIN_LEN_FLOAT) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "FLOAT" }));
        }
        int asInt = bytes[offset] & 0xff | (bytes[offset + 1] & 0xff) << 8 | (bytes[offset + 2] & 0xff) << 16 | (bytes[offset + 3] & 0xff) << 24;
        return vf.createFromDouble(Float.intBitsToFloat(asInt));
    }

    @Override
    public <T> T decodeDouble(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
        if (length != NativeConstants.BIN_LEN_DOUBLE) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "DOUBLE" }));
        }
        long valueAsLong = bytes[offset + 0] & 0xff | (long) (bytes[offset + 1] & 0xff) << 8 | (long) (bytes[offset + 2] & 0xff) << 16
                | (long) (bytes[offset + 3] & 0xff) << 24 | (long) (bytes[offset + 4] & 0xff) << 32 | (long) (bytes[offset + 5] & 0xff) << 40
                | (long) (bytes[offset + 6] & 0xff) << 48 | (long) (bytes[offset + 7] & 0xff) << 56;
        return vf.createFromDouble(Double.longBitsToDouble(valueAsLong));
    }

    @Override
    public <T> T decodeDecimal(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
        BigDecimal d = new BigDecimal(StringUtils.toAsciiCharArray(bytes, offset, length));
        return vf.createFromBigDecimal(d);
    }

    @Override
    public <T> T decodeByteArray(byte[] bytes, int offset, int length, Field f, ValueFactory<T> vf) {
        return vf.createFromBytes(bytes, offset, length, f);
    }

    @Override
    public <T> T decodeBit(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
        return vf.createFromBit(bytes, offset, length);
    }

    @Override
    public <T> T decodeSet(byte[] bytes, int offset, int length, Field f, ValueFactory<T> vf) {
        return decodeByteArray(bytes, offset, length, f, vf);
    }

    @Override
    public <T> T decodeYear(byte[] bytes, int offset, int length, ValueFactory<T> vf) {
        if (length != NativeConstants.BIN_LEN_INT2) {
            throw new DataReadException(Messages.getString("ResultSet.InvalidLengthForType", new Object[] { length, "YEAR" }));
        }
        short asShort = (short) (bytes[offset] & 0xff | (bytes[offset + 1] & 0xff) << 8);
        return vf.createFromYear(asShort);
    }

}
