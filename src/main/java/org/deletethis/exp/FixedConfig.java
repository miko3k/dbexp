package org.deletethis.exp;

/**
 *
 * @author miko
 */
public class FixedConfig implements DbConfig {

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    @Override
    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    @Override
    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    @Override
    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }


}
