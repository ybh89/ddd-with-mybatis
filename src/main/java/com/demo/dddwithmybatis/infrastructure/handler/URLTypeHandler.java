package com.demo.dddwithmybatis.infrastructure.handler;

import com.demo.dddwithmybatis.domain.model.URL;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(URL.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class URLTypeHandler extends BaseTypeHandler<URL>
{
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, URL parameter, JdbcType jdbcType) throws SQLException
    {
        ps.setString(i, parameter.value());
    }

    @Override
    public URL getNullableResult(ResultSet rs, String columnName) throws SQLException
    {
        return new URL(rs.getString(columnName));
    }

    @Override
    public URL getNullableResult(ResultSet rs, int columnIndex) throws SQLException
    {
        return new URL(rs.getString(columnIndex));
    }

    @Override
    public URL getNullableResult(CallableStatement cs, int columnIndex) throws SQLException
    {
        return new URL(cs.getString(columnIndex));
    }
}
