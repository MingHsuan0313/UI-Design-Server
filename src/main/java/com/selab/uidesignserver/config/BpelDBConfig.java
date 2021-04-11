package com.selab.uidesignserver.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {BpelDBConfig.Constant.PACKAGE_DAO},
        entityManagerFactoryRef = BpelDBConfig.Constant.ENTITY_MANAGER_FACTORY_BEAN,
        transactionManagerRef = BpelDBConfig.Constant.TRANSACTION_MANAGER_BEAN
)
public class BpelDBConfig {
    @Bean(name = Constant.DATASOURCE_BEAN)
    @ConfigurationProperties("spring.datasource.bpel")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = Constant.ENTITY_MANAGER_FACTORY_BEAN)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
            @Qualifier(Constant.DATASOURCE_BEAN) DataSource dataSource) {
        return builder.dataSource(dataSource).packages(Constant.PACKAGE_ENTITY)
                .persistenceUnit(Constant.PROJECT_TABLE)
                .persistenceUnit(Constant.BPEL_JSON_IR_TABLE)
                .persistenceUnit(Constant.PROJECT_BPEL_JSON_IR_TABLE)
                .build();
    }

    @Bean(name = Constant.TRANSACTION_MANAGER_BEAN)
    public PlatformTransactionManager transactionManager(
            @Qualifier(Constant.ENTITY_MANAGER_FACTORY_BEAN) EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    public static class Constant {
        // Bean name
        public static final String DATASOURCE_BEAN = "bpelDataSource";
        public static final String ENTITY_MANAGER_FACTORY_BEAN = "bpelEntityManagerFactory";
        public static final String TRANSACTION_MANAGER_BEAN = "bpelTransactionManager";

        // package name
        public static final String PACKAGE_DAO = "com.selab.uidesignserver.dao.bpel";
        public static final String PACKAGE_ENTITY = "com.selab.uidesignserver.entity.bpel";
        // table name
        public static final String PROJECT_TABLE = "project";
        public static final String BPEL_JSON_IR_TABLE = "bpel_json_ir";
        public static final String PROJECT_BPEL_JSON_IR_TABLE = "project_bpel_json_ir";
    }
}
