package br.com.trustsystems.database;

import java.util.Properties;

import br.com.trustsystems.configuration.ApplicationEnvironmentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;


@Component("dbproxy")
public class DatabaseProxyConfiguration extends PropertyPlaceholderConfigurer 
{

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseProxyConfiguration.class);

    public DatabaseProxyConfiguration() 
    {
        ApplicationEnvironmentHandler.createAppConfig();
        super.setLocation(new FileSystemResource(ApplicationEnvironmentHandler.getDatabaseConfigurationFile()));
        super.setLocalOverride(true);
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException 
    {
        String vendor = props.get(ApplicationEnvironmentHandler.DB_VENDOR).toString();
        String name = props.get(ApplicationEnvironmentHandler.DB_NAME).toString();
        String host = props.get(ApplicationEnvironmentHandler.DB_HOST).toString();
        String port = props.get(ApplicationEnvironmentHandler.DB_PORT).toString();

        DatabaseStrategy strategy = DatabaseVendor.valueOfParam(vendor).getStrategy();

        props.put("db.url", strategy.getURL(host, port, name));
        props.put("db.driver", strategy.getDriver());
        props.put("db.platform", strategy.getPersistencePlatform());
        props.put("db.testQuery", strategy.getTestQuery());

        for (Object p : props.keySet()) 
        {
            LOGGER.debug(p.toString() + ":" + props.getProperty(p.toString()));
        }

        super.processProperties(beanFactoryToProcess, props);
    }
}
