package org.deletethis.exp.db.generic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.deletethis.exp.DebugOut;
import org.deletethis.exp.db.source.SourceCursor;
import org.deletethis.exp.db.source.SourceException;

/**
 *
 * @author miko
 */
public class SourceSqlUtil {
    public static SourceCursor selectWholeTable(Connection conn, DebugOut debug, String tableName, List<String> columns, 
            String restOfSql) {
        
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("select ");
            boolean first = true;
            for(String col: columns) {
                if(first) {
                    first = false;
                } else {
                    builder.append(", ");
                }
                builder.append(col);
            }
            builder.append(" from ");
            builder.append(tableName);
            if(restOfSql != null) {
                builder.append(" ");
                builder.append(restOfSql);
            }
            String sql = builder.toString();
            Statement stmt = conn.createStatement();
            debug.println("executing query: `" + sql + "'");
            ResultSet rs = stmt.executeQuery(sql);
            return new SourceCursorImpl(rs);
        } catch (SQLException ex) {
            throw new SourceException(ex);
        }
    }    
}
