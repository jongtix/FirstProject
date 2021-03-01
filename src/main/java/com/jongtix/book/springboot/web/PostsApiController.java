package com.jongtix.book.springboot.web;

import com.jongtix.book.springboot.service.posts.PostsService;
import com.jongtix.book.springboot.web.dto.PostsResponseDto;
import com.jongtix.book.springboot.web.dto.PostsSaveRequestDto;
import com.jongtix.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public PostsResponseDto save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public PostsResponseDto update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) { return postsService.update(id, requestDto); }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) { return postsService.findById(id); }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

}
