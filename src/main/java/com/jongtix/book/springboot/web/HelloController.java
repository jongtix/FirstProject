package com.jongtix.book.springboot.web;

import com.jongtix.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //컨트롤러를 json을 반환하는 컨트롤러로 만들어주는 역할
public class HelloController {

    @GetMapping("/hello")   //HTTP Get 요청을 받을 수 있는 API를 만들어 줌
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name")  //외부에서 API로 넘긴 파라미터를 가져오는 어노테이션
                                                 String name,
                                     @RequestParam("amount")
                                             int amount) {
        return new HelloResponseDto(name, amount);
    }

}
