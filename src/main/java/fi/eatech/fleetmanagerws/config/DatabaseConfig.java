package fi.eatech.fleetmanagerws.config;

import fi.eatech.fleetmanagerws.model.tools.Tools;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Production database configuration
 * DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD had to be found in environment variables!
 *
 * @author Ville Nupponen
 * @since 0.0.1-SNAPSHOT
 */
@Profile("production")
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class DatabaseConfig {

    // From environment variables
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "DATABASE_PASSWORD";
    private static final String PROPERTY_NAME_DATABASE_URL = "DATABASE_URL";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "DATABASE_USERNAME";

    // From application.properties
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROPERTY_NAME_JPA_DATABASE = "hibernate.dialect";
    private static final String PROPERTY_NAME_JPA_HIBERNATE_DDLAUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_JPA_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

    private final Environment env;

    @Autowired
    public DatabaseConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setUrl(Tools.getEnv(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUsername(Tools.getEnv(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword(Tools.getEnv(PROPERTY_NAME_DATABASE_PASSWORD));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));

        entityManagerFactoryBean.setJpaProperties(hibProperties());

        return entityManagerFactoryBean;
    }

    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put(PROPERTY_NAME_JPA_DATABASE, env.getRequiredProperty(PROPERTY_NAME_JPA_DATABASE));
        properties.put(PROPERTY_NAME_JPA_HIBERNATE_DDLAUTO, env.getRequiredProperty(PROPERTY_NAME_JPA_HIBERNATE_DDLAUTO));
        properties.put(PROPERTY_NAME_JPA_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_JPA_SHOW_SQL));
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

}
