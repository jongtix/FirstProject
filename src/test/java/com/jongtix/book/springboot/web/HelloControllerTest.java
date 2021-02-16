package com.jongtix.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)    //테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자(SpringRunner)를 실행시킴
                                //스프링 부트 테스트와 JUnit 사이에 연결자 역할
@WebMvcTest //여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에 집중할 수 있는 어노테이션
            //선언할 경우 @Controller, @ControllerAdvice 등은 사용할 수 있음
            //@Service, @Component, @Repository 등은 사용할 수 없음
            //JAP 기능이 작동하지 않음
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;    //웹 API를 테스트할 때 사용
                            //스프링 MVC 테스트의 시작점
                            //HTTP GET, POST 등에 대한 API 테스트를 할 수 있음

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))            //MockMvc를 통해 /hello 주소로 HTTP GET 요청을 보냄
                .andExpect(status().isOk())             //mvc.perform의 HTTP Header Status 결과 검증
                .andExpect(content().string(hello));    //mvc.perform의 응답 본문 검증
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        //given
        String name = "hello";
        int amount = 1000;

        //when
        mvc.perform(get("/hello/dto")
                    .param("name", name)    //API 테스트할 때 사용될 요청 파라미터 설정
                    .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))    //jsonPath: JSON 응답값을 필드별로 검증할 수 있는 메소드
                                                                     //$를 기준으로 필드명을 명시
                .andExpect(jsonPath("$.amount", is(amount)));

    }
}
