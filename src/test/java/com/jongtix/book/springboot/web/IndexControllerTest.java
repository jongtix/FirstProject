package com.jongtix.book.springboot.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//Junit4
//@RunWith(SpringRunner.class)
//Junit5
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * For MockMvc START
     * @WithMockMvcUser를 사용하기 위해 MockMvc Setting
     */
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    /**
     * For MockMvc END
     */

    @Test
    public void 메인페이지_로딩() throws Exception {
        //when
        String body = restTemplate.getForObject("/", String.class);

        //then
        assertThat(body).contains("스프링 부트로 시작하는 웹서비스");
    }

    @Test
    @WithMockUser(roles = "USER")
    public void 저장페이지_로딩() throws Exception {
        //MockMvc 적용 후(TO-BE)
        //given
        String url = "/posts/save";

        //when
        //then
        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name("posts-save"));

/*
        //MockMvc 적용 전(AS-IS)
        //when
        String body = restTemplate.getForObject("/posts/save", String.class);

        //then
        assertThat(body).contains("등록");
*/
    }

}
