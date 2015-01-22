package br.com.trustsystems.configuration;

import br.com.trustsystems.database.DatabaseVendor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class ApplicationEnvironmentHandler
{
    private static final Logger logger = LoggerFactory.getLogger(ApplicationEnvironmentHandler.class);
    private static final File APPLICATION_ENVIRONMENT_DIRECTORY = new File(System.getProperty("user.home")+File.separator+"tapestryspring");
    private static final File DATABASE_CONFIGURATION_FILE = new File(APPLICATION_ENVIRONMENT_DIRECTORY, "database.properties");
    
    public static final String DB_VENDOR = "db.vendor";
    public static final String DB_HOST = "db.host";
    public static final String DB_PORT = "db.port";
    public static final String DB_NAME = "db.name";
    public static final String DB_USER = "db.user";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_SHOWSQL = "db.showsql";
    public static final String DB_GENERATE_SCHEMA = "db.generateSchema";
    
    private static final Map<String, String> CONNECTION_DEFAULTS = new LinkedHashMap<String, String>();

    private static final String COMMENTS = 
            "##########################################################\n"
            + "db.vendor: RDBMS Vendor;\n"
            + "db.host:   RDBMS Host;\n"
            + "db.port: RDBMS PORT;\n"
            + "db.name: Database Name;\n"
            + "db.user: RDBMS username;\n"
            + "db.password: RDBMS password;\n"
            + "db.showsql: RDBMS show query in console;\n"
            + "db.generateSchema: RDBMS automatic schema generate,\n"
            + "##########################################################\n";

    static 
    {
        CONNECTION_DEFAULTS.put(DB_VENDOR, DatabaseVendor.MYSQL.toString());
        CONNECTION_DEFAULTS.put(DB_HOST, "localhost");
        CONNECTION_DEFAULTS.put(DB_PORT, "3306");
        CONNECTION_DEFAULTS.put(DB_NAME, "");
        CONNECTION_DEFAULTS.put(DB_USER, "");
        CONNECTION_DEFAULTS.put(DB_PASSWORD, "");
        CONNECTION_DEFAULTS.put(DB_SHOWSQL, "false");
        CONNECTION_DEFAULTS.put(DB_GENERATE_SCHEMA, "");
    }

    public static File getDatabaseConfigurationFile()
    {
        return DATABASE_CONFIGURATION_FILE;
    }

    public static File getApplicationEnvironmentDirectory()
    {
        return APPLICATION_ENVIRONMENT_DIRECTORY;
    }
    
    public static void createAppConfig()
    {
        final long startTime = System.currentTimeMillis();
        logger.info(ApplicationEnvironmentHandler.class.getSimpleName() + ": initialization started");

        try {
            FileUtils.forceMkdir(APPLICATION_ENVIRONMENT_DIRECTORY);

            File connectionFile = DATABASE_CONFIGURATION_FILE;

            if (!connectionFile.exists()) {
                logger.info("Configuration file being generated in: [" + connectionFile.getAbsolutePath() + "]");

                Properties props = new Properties();
                props.put(DB_VENDOR, DatabaseVendor.MYSQL.toString());
                props.put(DB_HOST, "127.0.0.1");
                props.put(DB_PORT, "3306");
                props.put(DB_NAME, "test");
                props.put(DB_USER, "root");
                props.put(DB_PASSWORD, "");
                props.put(DB_SHOWSQL, "false");
                props.put(DB_GENERATE_SCHEMA, "");

                store(props, connectionFile);

            } else {
                logger.info("Configuration file located in: [" + connectionFile.getAbsolutePath() + "]");
            }

            final long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info(ApplicationEnvironmentHandler.class.getSimpleName() + ": initialization completed in " + elapsedTime + " ms");

            checkAppConfig(connectionFile);

        } catch (FileNotFoundException e) {
            logger.error("File not found!", e);
        } catch (IOException e) {
            logger.error("I/O error!", e);
        }
    }
    
    public static void checkAppConfig(File appConfigFile) throws FileNotFoundException, IOException
    {
        if (appConfigFile.exists()) 
        {
            Properties appConfigProps = new Properties();
            appConfigProps.load(new FileInputStream(appConfigFile));
            
            for (String key : CONNECTION_DEFAULTS.keySet())
            {
                if (!appConfigProps.containsKey(key)) 
                {
                    appConfigProps.put(key, CONNECTION_DEFAULTS.get(key));
                    store(appConfigProps, appConfigFile);
                }
            }
            
            Iterator<Map.Entry<Object, Object>> iterator = appConfigProps.entrySet().iterator();
            while (iterator.hasNext())
            {
                Map.Entry<Object, Object> entry = iterator.next();
                if (!CONNECTION_DEFAULTS.containsKey(entry.getKey())) 
                {
                    iterator.remove();
                    store(appConfigProps, appConfigFile);
                }
            }
        }
    }
    
    private static void store(Properties property, File file) throws FileNotFoundException, IOException 
    {
        property.store(new FileOutputStream(file), COMMENTS);
    }
}
