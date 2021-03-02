package com.jongtix.book.springboot.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QuerydslConfig {   //프로젝트 어디에서나 JPAQueryFactory를 주입 받아 Querydsl을 사용할 수 있도록 해주는 설정
                                //출처: https://jojoldu.tistory.com/372

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

}
