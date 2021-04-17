package br.com.trustsystems.database;

public class MySQLStrategy implements DatabaseStrategy
{
    @Override
    public String getPersistencePlatform()
    {
        return org.eclipse.persistence.platform.database.MySQLPlatform.class.getName();
    }

    @Override
    public String getDriver()
    {
        return com.mysql.cj.jdbc.MysqlDataSource.class.getName();
    }

    @Override
    public String getTestQuery() 
    {
        return "SELECT 1";
    }

    @Override
    public String getURL(String host, String port, String db) 
    {
        return "jdbc:mysql://" + host + ":" + port + "/" + db;
    }
}
