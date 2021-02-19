package com.jongtix.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)  //이 어노테이션이 생성될 수 있는 위치 지정
@Retention(RetentionPolicy.RUNTIME) //어느 시점까지 어노테이션의 메모리를 가져갈 지 설정
                                    //출처: https://sas-study.tistory.com/329
public @interface LoginUser {
}
