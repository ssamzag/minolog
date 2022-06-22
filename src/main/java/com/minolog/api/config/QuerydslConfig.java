package com.minolog.api.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QuerydslConfig {
    @PersistenceContext
    public EntityManager em;

    @Bean
    public JPAQueryFactory JPAQueryFactory() {
        return new JPAQueryFactory(em);
    }
}
