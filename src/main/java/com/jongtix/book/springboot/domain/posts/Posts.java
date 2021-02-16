package com.jongtix.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//Entity 클래스에서는 Setter 메서드를 절대 사용하지 않음
@Getter
@NoArgsConstructor  //parameter 없는 기본 생성자 자동 추가
@Entity //테이블과 링크될 클래스임을 나타냄
public class Posts {

    @Id //해당 테이블의 PK 필드임을 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK 생성 규칙GenerationType.IDENTITY: auto_increment
    private Long id;

    @Column(length = 500, nullable = false) //해당 테이블의 칼럼을 나타냄
                                            //필수는 아니지만 기본값 외에 추가로 변경이 필요한 옵션이 있을 때 사용
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder    //해당 클래스의 빌더 패턴 클래스를 생성
    public Posts(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

}
