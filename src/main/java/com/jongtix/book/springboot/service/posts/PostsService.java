package com.jongtix.book.springboot.service.posts;

import com.jongtix.book.springboot.domain.posts.Posts;
import com.jongtix.book.springboot.domain.posts.PostsRepository;
import com.jongtix.book.springboot.web.dto.PostsListResponseDto;
import com.jongtix.book.springboot.web.dto.PostsResponseDto;
import com.jongtix.book.springboot.web.dto.PostsSaveRequestDto;
import com.jongtix.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public PostsResponseDto save(PostsSaveRequestDto requestDto) {
        return new PostsResponseDto(postsRepository.save(requestDto.toEntity()));
    }

    @Transactional
    public PostsResponseDto update(Long id, PostsUpdateRequestDto requestDto) {

        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
/*
        PostsUpdateRequestDto newEntity = PostsUpdateRequestDto.builder()
                .id(posts.getId())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .author(requestDto.getAuthor())
                .build();
*/

/**
 * update Method에서 데이터베이스에 쿼리를 날리지 않아도 되는 이유 -> JPA의 영속성 컨텍스트 때문
 * 영속성 컨텍스트: 엔티티를 영구 저장하는 환경
 * JPA의 엔티티 매니저가 활성화된 상태(Spring Data Jpa는 기본 옵션)로 트랜젝션 안에서 데이터베이스의 데이터를 가져오면
 * 이 데이터는 영속성 컨텍스트가 유지된 상태
 * 이 상태에서 해당 데이터의 값을 변경하면 트랜젝션이 끝나는 시점에 해당 테이블에 변경분을 반영함
 * 즉, 엔티티 객체의 값만 변경하면 별도로 Update 쿼리를 날릴 필요가 없음 == 더티 체킹
 */
//        return postsRepository.save(newEntity.toEntity()).getId();
        return new PostsResponseDto(posts);
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true) //readOnly = true: 트랜잭션 범위는 유지하되, 조회 기능만 남겨두어 조회 속도가 개선
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));
        postsRepository.delete(posts);
    }
}
