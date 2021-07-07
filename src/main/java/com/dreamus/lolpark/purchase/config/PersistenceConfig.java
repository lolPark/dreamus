package com.dreamus.lolpark.purchase.config;

import com.dreamus.lolpark.purchase.constants.JpaSettingConstants;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = "purchaseEntityManagerFactory",
    transactionManagerRef = "purchaseTransactionManager",
    basePackages = "com.dreamus.lolpark.purchase.repository"
)
@EnableTransactionManagement
@RequiredArgsConstructor
public class PersistenceConfig {

    private static final String ENTITY_PACKAGE = "com.dreamus.lolpark.purchase.domain";
    private static final String PERSISTENCE_UNIT = "purchasePersistenceUnit";

    private final DataSource purchaseDataSource;

    @Primary
    @Bean(name = "purchaseEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final EntityManagerFactoryBuilder builder) {
        return builder
            .dataSource(purchaseDataSource)
            .packages(ENTITY_PACKAGE)
            .persistenceUnit(PERSISTENCE_UNIT)
            .properties(getVendorProperties())
            .build();
    }

    // ============================================ Transaction Managers ============================================
    @Primary
    @Bean(name = "purchaseTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("purchaseEntityManagerFactory")
                                                             EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    // ========================================= Spring JDBC Template =========================================
    @Primary
    @Bean(name = "purchaseNamedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(purchaseDataSource);
    }

    // ============================================ ETC ============================================
    // hibernate props
    private Map<String, Object> getVendorProperties() {
        Map<String, Object> prop = new HashMap<>();
        prop.put(AvailableSettings.DIALECT, JpaSettingConstants.DIALECT_H2);
        prop.put(AvailableSettings.USE_SECOND_LEVEL_CACHE, false);
        prop.put(AvailableSettings.USE_QUERY_CACHE, false);
        prop.put(AvailableSettings.ENABLE_LAZY_LOAD_NO_TRANS, true);
        prop.put(AvailableSettings.IMPLICIT_NAMING_STRATEGY, JpaSettingConstants.DEFAULT_IMPLICIT_NAMING_STRATEGY);
        prop.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, JpaSettingConstants.DEFAULT_PHYSICAL_NAMING_STRATEGY);

        prop.put(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS, true);
        prop.put(AvailableSettings.HBM2DDL_AUTO, JpaSettingConstants.DDL_AUTO_CREATE_DROP);
        prop.put(AvailableSettings.FORMAT_SQL, true);

        return prop;
    }
}
