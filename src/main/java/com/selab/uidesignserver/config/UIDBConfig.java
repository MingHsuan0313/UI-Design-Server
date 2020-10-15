package com.selab.uidesignserver.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
transactionManagerRef = "uiTransactionManager" ,
entityManagerFactoryRef = "uiEntityManagerFactory", 
basePackages = {"com.selab.uidesignserver.dao.uiComposition" })
public class UIDBConfig {

    // @Primary
    @Bean(name = "uiDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.ui")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    // @Primary
    @Bean(name = "uiEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
            @Qualifier("uiDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.selab.uidesignserver.entity.uiComposition")
                .persistenceUnit("navigation").persistenceUnit("pages").persistenceUnit("templates").build();
    }

    // @Primary
    @Bean(name = "uiTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("uiEntityManagerFactory") EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
        // return new JpaTransactionManager(entityManagerFactory);
    }
}