package com.jongtix.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {   //JpaRepository<Entity 클래스, PK 타입>: 상속하면 기본적인 CRUD 메서드가 자동으로 생성됨
                                                                        //Entity 클래스와 Entity Repository는 함께 위치
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();

}
