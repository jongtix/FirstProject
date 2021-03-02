package com.jongtix.book.springboot.domain.posts;

import java.util.List;

public interface PostsRepositoryCustom {    //querydsl 사용 방법 2

    List<Posts> findByAuthor(String author);

}
