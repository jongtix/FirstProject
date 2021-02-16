package com.jongtix.book.springboot.web.dto;

import com.jongtix.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entity 클래스와 거의 유사한 형태임에도 Dto 클래스를 추가로 생성한 이유:
 * Entity 클래스는 DB와 맞닿은 핵심 클래스
 * Entity 클래스를 기준으로 테이블이 생성되고, 스키마가 변경
 * 화면 변경은 아주 사소한 기능 변경인데, 이를 위해 테이블과 연결된 Entity 클래스를 변경하는 것은 너무 큰 변경이기 때문
 * 수많은 서비스 클래스나 비즈니스 로직들이 Entity 클래스를 기준으로 동작함
 * Entity 클래스가 변경되면 여러 클래스에 영향을 끼지지만, Request와 Response용 Dto는 View를 위한 클래스라 정말 자주 변경이 필요함
 * View Layer와 DB Layer의 역할 분리를 철저하게 하는 게 좋음
 * 실제로 Controller에서 결괏값으로 여러 테이블을 조인해서 줘야할 경우 Entity 클래스만으로 표현하기가 어려운 경우가 많음
 */
@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}
