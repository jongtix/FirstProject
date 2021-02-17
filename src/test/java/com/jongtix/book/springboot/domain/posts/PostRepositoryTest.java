package com.jongtix.book.springboot.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest //@SpringBootTest를 사용할 경우 H2 데이터베이스를 자동으로 실행
public class PostRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After  //Junit에서 단위 테스트가 끝날 때마다 수행되는 메소드
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글_내림차순하여_불러오기() throws Exception {
        //given
        postsRepository.save(Posts.builder()
                .title("title1")
                .content("content6")
                .author("author8")
                .build());
        postsRepository.save(Posts.builder()
                .title("title2")
                .content("content5")
                .author("author7")
                .build());
        postsRepository.save(Posts.builder()
                .title("title3")
                .content("content4")
                .author("author9")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAllDesc();

        //then
        assertThat(postsList.get(0).getId()).isGreaterThan(postsList.get(1).getId());
        assertThat(postsList.get(1).getId()).isGreaterThan(postsList.get(2).getId());

    }

    @Test
    public void 게시글저장_불러오기() throws Exception {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                            .title(title)
                            .content(content)
                            .author("jong1145@naver.com")
                            .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.of(2021, 2, 17, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println("createDate >>> " + posts.getCreatedDate() + ", modifiedDate >>> " + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }

}
