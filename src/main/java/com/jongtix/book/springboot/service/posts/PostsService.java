package com.jongtix.book.springboot.service.posts;

import com.jongtix.book.springboot.domain.posts.Posts;
import com.jongtix.book.springboot.domain.posts.PostsRepository;
import com.jongtix.book.springboot.web.dto.PostsResponseDto;
import com.jongtix.book.springboot.web.dto.PostsSaveRequestDto;
import com.jongtix.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity())
                              .getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {

        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));
        PostsUpdateRequestDto newEntity = PostsUpdateRequestDto.builder()
                .id(posts.getId())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .author(requestDto.getAuthor())
                .build();

        return postsRepository.save(newEntity.toEntity()).getId();
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));
        return new PostsResponseDto(entity);
    }
}
