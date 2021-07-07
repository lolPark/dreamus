package com.dreamus.lolpark.purchase.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class QuerydslConfig {

    @Bean
    public JPAQueryFactory queryFactory(@Qualifier("purchaseEntityManagerFactory") EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
