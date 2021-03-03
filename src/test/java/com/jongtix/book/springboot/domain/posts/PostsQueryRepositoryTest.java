package com.jongtix.book.springboot.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostsQueryRepositoryTest {

    @Autowired
    private PostsRepository repository;

    @Autowired
    private PostsQueryRepository queryRepository;

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }

    @DisplayName("querydsl_기본_기능_확인_2")
    @Test
    public void querydsl_select_by_author_check() {
        //given
        String title = "title";
        String content = "content";
        String author = "author";

        repository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        //when
        List<Posts> postsList = queryRepository.findByAuthor(author);

        //then
        assertThat(postsList.get(0).getTitle()).isEqualTo(title);
        assertThat(postsList.get(0).getContent()).isEqualTo(content);
        assertThat(postsList.get(0).getAuthor()).isEqualTo(author);
    }

}