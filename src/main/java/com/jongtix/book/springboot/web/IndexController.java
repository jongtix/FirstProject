package com.jongtix.book.springboot.web;

import com.jongtix.book.springboot.config.auth.LoginUser;
import com.jongtix.book.springboot.config.auth.dto.SessionUser;
import com.jongtix.book.springboot.domain.user.User;
import com.jongtix.book.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {  //Model: 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있음
        model.addAttribute("posts", postsService.findAllDesc());

        //AS-IS
//        //로그인 관련 userName 추가
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

/*
    @GetMapping("/")
    public String index() {
        return "index"; //spring-boot-starter-mustache에서 자동으로 앞에 src/main/resources/templates와 뒤에 .mustache를 붙여줌
    }
*/

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        model.addAttribute("post", postsService.findById(id));
        return "posts-update";
    }

}
