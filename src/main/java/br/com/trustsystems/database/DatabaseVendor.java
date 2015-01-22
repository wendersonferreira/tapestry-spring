package br.com.trustsystems.database;

import org.springframework.util.StringUtils;

public enum DatabaseVendor 
{

    MYSQL {
        @Override
        public DatabaseStrategy getStrategy() 
        {
            return new MySQLStrategy();
        }
    };

    public static DatabaseVendor valueOfParam(String vendor) 
    {
        if (!StringUtils.hasText(vendor)) 
        {
            throw new IllegalArgumentException("Database provider not found!");
        }

        for (DatabaseVendor dbVendor : values()) 
        {
            if (dbVendor.toString().equals(vendor.toUpperCase())) {
                return dbVendor;
            }
        }
        throw new IllegalArgumentException("Database type not supported by the system!");
    }

    public abstract DatabaseStrategy getStrategy();
}
