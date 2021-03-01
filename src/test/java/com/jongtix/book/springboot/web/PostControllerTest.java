package com.jongtix.book.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jongtix.book.springboot.domain.posts.Posts;
import com.jongtix.book.springboot.domain.posts.PostsRepository;
import com.jongtix.book.springboot.web.dto.PostsResponseDto;
import com.jongtix.book.springboot.web.dto.PostsSaveRequestDto;
import com.jongtix.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static com.jongtix.book.springboot.utils.ApiDocumentUtils.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

//Junit4
//@RunWith(SpringRunner.class)
//Junit5
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostControllerTest {

    /**
     * For MockMvc START
     * @WithMockMvcUser를 사용하기 위해 MockMvc Setting
     */

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    //Junit4
    //@Before
    //Junit5
//    @BeforeEach
//    public void setup() {
//        mvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }

    @BeforeEach
    public void setup(RestDocumentationContextProvider restDocumentation) {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

/*
    @Test
    @WithMockUser(roles = "USER")
    public void Posts_수정된다_MockMvc() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updatedId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updatedId;

        //when
        mvc.perform(put(url)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
*/
    /**
     * For mockMvc END
     */

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    //Junit4
    //@After
    //Junit5
    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @DisplayName("Posts_등록된다")
    @Test
    @WithMockUser(roles = "USER")   //인증된 모의 사용자를 만들어 사용함
                                    //MockMvc에서만 작동
    public void save_posts() throws Exception {
        //given
        String title = "title";
        String content = "content";
        String author = "author";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                                                            .title(title)
                                                            .content(content)
                                                            .author(author)
                                                            .build();

        //Spring REST Docs 적용 후(TO-BE)
        String url = "http://localhost:" + port + "/api/v1/posts";

        //when
        ResultActions result = mvc.perform(
                post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto))
        );

        //then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}"
                        , getDocumentRequest()
                        , getDocumentResponse()
                        , responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("author").type(JsonFieldType.STRING).description("작성자")
                        )
                ))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.author").value(author));

//        //Spring REST Docs 적용 전(AS-IS)
//        //MockMvc 적용 후(TO-BE)
//        //when
//        mvc.perform(post(url)   //생성된 MockMvc를 통해 API를 테스트
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(requestDto)))    //본문(Body) 영역을 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON을 변환
//            .andExpect(status().isOk());
//
//        //then
//        List<Posts> all = postsRepository.findAll();
//        assertThat(all.get(0).getTitle()).isEqualTo(title);
//        assertThat(all.get(0).getContent()).isEqualTo(content);
/*
        //MockMvc 적용 전(AS-IS)
        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
*/
    }

    @DisplayName("Posts_수정된다")
    @Test
    @WithMockUser(roles = "USER")
    public void update_posts() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updatedId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        //Spring REST Docs 적용 후(TO-BE)
//        String url = "http://localhost:" + port + "/api/v1/posts/" + updatedId;
        String url = "http://localhost:" + port + "/api/v1/posts/{id}";

        //when
        ResultActions result = mvc.perform(
                put(url, updatedId)
                    .content(new ObjectMapper().writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}"
                        , getDocumentRequest()
                        , getDocumentResponse()
                        , pathParameters(
                                parameterWithName("id").description("아이디")
                        )
                        , responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("author").type(JsonFieldType.STRING).description("작성자")
                        )
                ))
                .andExpect(jsonPath("$.title").value(expectedTitle))
                .andExpect(jsonPath("$.content").value(expectedContent))
                .andExpect(jsonPath("$.author").value("author"));

//        //Spring REST Docs 적용 전(AS-IS)
//        //MockMvc 적용 후(TO-BE)
//        //when
//        mvc.perform(put(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(requestDto)))
//            .andExpect(status().isOk());
////            .andExpect(jsonPath("$.title").value(expectedTitle))
////            .andExpect(jsonPath("$.content").value(expectedContent))
////            .andExpect(jsonPath("$.author").value("author"));
//
//        //then
//        List<Posts> all = postsRepository.findAll();
//        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
//        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
//        assertThat(all.get(0).getAuthor()).isEqualTo("author");

/*
        //MockMvc 적용 전(AS-IS)
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
        assertThat(all.get(0).getAuthor()).isEqualTo("author");
*/
    }

    @DisplayName("Posts_삭제된다")
    @Test
    @WithMockUser(roles = "USER")
    public void delete_posts() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long deletedId = savedPosts.getId();

        //Spring REST Docs 적용 후(TO-BE)
//        String url = "http://localhost:" + port + "/api/v1/posts/" + deletedId;
        String url = "http://localhost:" + port + "/api/v1/posts/{id}";

        //when
        ResultActions result = mvc.perform(
                delete(url, deletedId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}"
                        , getDocumentRequest()
                        , getDocumentResponse()
                        , pathParameters(
                                parameterWithName("id").description("아이디")
                        )
                ))
                .andExpect(jsonPath("$").value(deletedId));


        //Spring REST Docs 적용 전(AS-IS)
//        //MockMvc 적용 후(TO-BE)
//        //when
//        mvc.perform(delete(url))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value(deletedId));
//
//        //then
//        List<Posts> all = postsRepository.findAll();
//        assertThat(all.size()).isLessThanOrEqualTo(0);
/*
        //MockMvc 적용 전(AS-IS)
        //when
        restTemplate.delete(url);

        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
*/
    }

    @DisplayName("Posts_가져온다")
    @Test
    @WithMockUser(roles = "USER")
    public void get_posts() throws Exception {
        //given
        String expectedTitle = "title";
        String expectedContent = "content";
        String expectedAuthor = "author";

        Posts savedPosts = postsRepository.save(Posts.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .author(expectedAuthor)
                .build());

        Long savedId = savedPosts.getId();

        //Spring REST Docs 적용 후(TO-BE)
//        String url = "http://localhost:" + port + "/api/v1/posts/" + savedId;
        String url = "http://localhost:" + port + "/api/v1/posts/{id}";

        //when
        ResultActions result = mvc.perform(
                get(url, savedId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("{class-name}/{method-name}"
                        , getDocumentRequest()
                        , getDocumentResponse()
                        , pathParameters(
                            parameterWithName("id").description("아이디")
                        )
//                        , requestFields(
//                                fieldWithPath("body").type(JsonFieldType.STRING).description("바디")
//                        )
                        , responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("author").type(JsonFieldType.STRING).description("작성자")
                        )
                ))
            .andExpect(jsonPath("$.title").value(expectedTitle))
            .andExpect(jsonPath("$.content").value(expectedContent))
            .andExpect(jsonPath("$.author").value(expectedAuthor));

        //Spring REST Docs 적용 전(AS-IS)
//        //MockMvc 적용 후(TO-BE)
//        //when
//        //then
//        mvc.perform(get(url))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.title").value(expectedTitle))
//            .andExpect(jsonPath("$.content").value(expectedContent))
//            .andExpect(jsonPath("$.author").value(expectedAuthor));

/*
        //MockMvc 적용 전(AS-IS)
        //when
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        //소셜 로그인 추가 이후 or Junit5로 마이그래이션 이후 작동 불능
//        PostsResponseDto responseDto = restTemplate.getForObject(url, PostsResponseDto.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotEmpty();
//        assertThat(responseDto.getTitle()).isEqualTo("title");
//        assertThat(responseDto.getContent()).isEqualTo("content");
//        assertThat(responseDto.getAuthor()).isEqualTo("author");
*/

        /*
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String saveUrl = "http://localhost:" + port + "/api/v1/posts";
        ResponseEntity<Long> responseEntityForSave = restTemplate.postForEntity(saveUrl, requestDto, Long.class);

        Long id = responseEntityForSave.getBody();
        String findUrl = "http://localhost:" + port + "/api/v1/posts/" + id;

        //when
        ResponseEntity<String> responseEntityForFind = restTemplate.getForEntity(findUrl, String.class);

        assertThat(responseEntityForFind.getStatusCode()).isEqualTo(HttpStatus.OK);
//        System.out.println(responseEntityForFind.getBody());

//        postsRepository.findById(id);
*/

    }

}
