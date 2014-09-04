package org.deletethis.exp.db.oracle;

import java.math.BigInteger;
import org.deletethis.exp.db.source.ColumnSchema;
import org.deletethis.exp.db.source.ColumnType;
import org.deletethis.exp.db.source.TableSchema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.deletethis.exp.DebugOut;
import org.deletethis.exp.db.generic.SourceSqlUtil;
import org.deletethis.exp.db.source.SourceCursor;
import org.deletethis.exp.db.source.SourceException;
import org.deletethis.exp.db.source.SourceSql;

/**
 *
 * @author miko
 */
public class OracleSource implements SourceSql {
    private final Connection connection;
    private final DebugOut debug;

    public OracleSource(Connection connection, DebugOut debug) {
        this.connection = connection;
        this.debug = debug;
    }
    
    private ColumnType getColumnType(String name)
    {
        switch(name) {
            case "NUMBER": return ColumnType.NUMBER;
            case "DATE": return ColumnType.DATE;
            case "VARCHAR2": return ColumnType.STRING;
            case "CLOB": return ColumnType.CLOB;
            case "BLOB": return ColumnType.BLOB;
            default: throw new IllegalArgumentException("column: " + name);
        }
    }
    
    @Override
    public TableSchema getTableSchema(String table) {
        try {
            String select
                    = "select column_name, data_type from user_tab_cols where table_name = ?";

            PreparedStatement stmt = connection.prepareStatement(select);
            stmt.setString(1, table);

            ResultSet rs = stmt.executeQuery();
            List<ColumnSchema> cols = new ArrayList<>();

            boolean something = false;
            while (rs.next()) {
                something = true;
                String a = rs.getString(1);
                String b = rs.getString(2);
                cols.add(new ColumnSchema(a, getColumnType(b)));
            }
            if(!something)
                throw new IllegalArgumentException("table '" + table + "' does not exist");

            return new TableSchema(table, cols);
        } catch (SQLException e) {
            throw new SourceException(e);
        }
    }

    @Override
    public SourceCursor selectWholeTable(String tableName, List<String> columns, 
            String restOfSql) {
        return SourceSqlUtil.selectWholeTable(connection, debug, tableName, columns, restOfSql);
    }
    
    @Override
    public BigInteger getSequenceValue(String sequenceName)
    {
        try {
            String select
                    = "select last_number from USER_SEQUENCES where SEQUENCE_NAME = ?";

            PreparedStatement stmt = connection.prepareStatement(select);
            stmt.setString(1, sequenceName);

            ResultSet rs = stmt.executeQuery();
            BigInteger result = null;

            while (rs.next()) {
                result = rs.getBigDecimal(1).toBigInteger();
            }
            if(result == null)
                throw new IllegalArgumentException("sequence '" + sequenceName + "' does not exist");
            
            return result;
        } catch (SQLException ex) {
            throw new SourceException(ex);
        }
    }
}
