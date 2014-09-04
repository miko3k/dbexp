package org.deletethis.exp.script.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import oracle.jdbc.pool.OracleDataSource;
import org.deletethis.exp.JdbcUtil;
import org.deletethis.exp.DbConfig;
import org.deletethis.exp.db.oracle.OracleSource;
import org.deletethis.exp.db.source.SourceSql;
import org.deletethis.exp.script.Command;
import org.deletethis.exp.script.CommandException;
import org.deletethis.exp.script.ExecutionState;

/**
 *
 * @author miko
 */
public class OracleConnectionCommand implements Command, DbConfig {
    private final String user,password,dburl;

    public OracleConnectionCommand(String user, String password, String dbstr) {
        this.user = Objects.requireNonNull(user);
        this.password = Objects.requireNonNull(password);
        this.dburl = Objects.requireNonNull(dbstr);
    }

    @Override
    public void execute(ExecutionState state) throws CommandException {
        try {
            OracleDataSource dataSource = JdbcUtil.connectToOracle(this);
            Connection connection = dataSource.getConnection();
            
            SourceSql src = new OracleSource(connection, state.getDebug());
            state.setSourceSql(src);

        } catch (SQLException ex) {
            throw new CommandException("unable to connect to database", ex);
        }
    }

    @Override
    public String getDbUrl() {
        return dburl;
    }

    @Override
    public String getDbUser() {
        return user;
    }

    @Override
    public String getDbPassword() {
        return password;
    }
    
    
    
}
