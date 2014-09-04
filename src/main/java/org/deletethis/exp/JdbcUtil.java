package org.deletethis.exp;

import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author miko
 */
public class JdbcUtil {
    public static final OracleDataSource connectToOracle(DbConfig db) throws SQLException
    {
        OracleDataSource ods;

        ods = new OracleDataSource();
        ods.setURL(db.getDbUrl());
        ods.setUser(db.getDbUser());
        ods.setPassword(db.getDbPassword());
        
        return ods;
    }
}