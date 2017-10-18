package com.tuniu.qms.common.util.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;

public class ArrayTypeHandler extends BaseTypeHandler<Object[]> {

    private static final String TYPE_NAME_VARCHAR = "varchar";
    private static final String TYPE_NAME_INTEGER = "integer";
    private static final String TYPE_NAME_BOOLEAN = "boolean";
    private static final String TYPE_NAME_NUMERIC = "numeric";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object[] parameter, JdbcType jdbcType) throws SQLException {
        String typeName = null;
        if(parameter instanceof Integer[]) {
            typeName = TYPE_NAME_INTEGER;
        } else if(parameter instanceof String[]) {
            typeName = TYPE_NAME_VARCHAR;
        } else if(parameter instanceof Boolean[]) {
            typeName = TYPE_NAME_BOOLEAN;
        } else if(parameter instanceof Double[]) {
            typeName = TYPE_NAME_NUMERIC;
        }

        if(typeName == null) {
            throw new TypeException("ArrayTypeHandler parameter typeName error, your type is " + parameter.getClass().getName());
        }

        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < parameter.length; j++) {
            if(j == parameter.length - 1) {
                sb.append(parameter[j]);
            } else {
                sb.append(parameter[j]).append(";");
            }
        }
        ps.setString(i, sb.toString());
    }

    @Override
    public Object[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getArray(rs.getString(columnName));
    }

    @Override
    public Object[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getArray(rs.getString(columnIndex));
    }

    @Override
    public Object[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getArray(cs.getString(columnIndex));
    }

    private Object[] getArray(String str) {
        Object[] objArr = null;
        if(StringUtils.isNotEmpty(str)) {
            objArr = (Object[]) (str.split(";"));
        }
        return objArr;
    }

}
