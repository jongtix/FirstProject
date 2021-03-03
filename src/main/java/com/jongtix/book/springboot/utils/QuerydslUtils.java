package com.jongtix.book.springboot.utils;

import com.jongtix.book.springboot.domain.posts.Posts;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;

public class QuerydslUtils {

    //Sort Field 지정
    //출처: https://stackoverflow.com/questions/39576236/dynamic-sorting-in-querydsl
    public static OrderSpecifier<?> getSortedColumn(Order order, EntityPathBase<?> qEntity, String fieldName) {
        PathBuilder<?> pathBuilder = new PathBuilder<Object>(Posts.class, "");
        Path<Object> fieldPath = Expressions.path(Object.class, qEntity, fieldName);
        return new OrderSpecifier(order, fieldPath);
    }

}
