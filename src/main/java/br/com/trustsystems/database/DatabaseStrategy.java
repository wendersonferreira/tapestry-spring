package br.com.trustsystems.database;

public interface DatabaseStrategy
{
    String getPersistencePlatform();

    String getDriver();

    String getTestQuery();

    String getURL(String host, String port, String db);
}