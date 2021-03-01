package com.jongtix.book.springboot.web;

import com.jongtix.book.springboot.utils.ApiDocumentUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static com.jongtix.book.springboot.utils.ApiDocumentUtils.*;

//Junit4
//@RunWith(SpringRunner.class)
//Junit5
//@ExtendWith(SpringExtension.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})  //Spring REST Docs 사용을 위한 1 단계: RestDocumentationExtension.class 적용
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

    //Spring REST Docs 적용 작업 AS-IS
//    @BeforeEach
//    public void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }

    //Spring REST Docs 적용 작업 TO-BE
    @BeforeEach
    public void setup(RestDocumentationContextProvider restDocumentation) {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }
    /**
     * For MockMvc END
     */

    @DisplayName("메인페이지_로딩")
    @Test
    public void load_main_page() throws Exception {
        //when
        String body = restTemplate.getForObject("/", String.class);

        //then
        assertThat(body).contains("스프링 부트로 시작하는 웹서비스");
    }

    @DisplayName("저장페이지_로딩")
    @Test
    @WithMockUser(roles = "USER")
    public void load_save_page() throws Exception {
        //MockMvc 적용 후(TO-BE)
        //given
        String url = "/posts/save";

        //when
        ResultActions result = mvc.perform(
                get(url)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andExpect(view().name("posts-save"))
                .andDo(print())
                .andDo(document("{class-name}/{method-name}"
                        , getDocumentRequest()
                        , getDocumentResponse()));

//        //when
//        //then
//        mvc.perform(get(url))
//                .andExpect(status().isOk())
//                .andExpect(view().name("posts-save"))
/*
        //MockMvc 적용 전(AS-IS)
        //when
        String body = restTemplate.getForObject("/posts/save", String.class);

        //then
        assertThat(body).contains("등록");
*/
    }

}
