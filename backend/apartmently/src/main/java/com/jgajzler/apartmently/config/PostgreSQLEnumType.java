package com.jgajzler.apartmently.config;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class PostgreSQLEnumType extends org.hibernate.type.EnumType {

    public void nullSafeSet(
            PreparedStatement preparedStatement,
            Object value,
            int index,
            SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            preparedStatement.setNull(index, Types.OTHER);
        } else {
            preparedStatement.setObject(
                    index,
                    value.toString(),
                    Types.OTHER
            );
        }
    }
}
