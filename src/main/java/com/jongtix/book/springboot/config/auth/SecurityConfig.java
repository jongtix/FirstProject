package com.jongtix.book.springboot.config.auth;

import com.jongtix.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  //Spring Security 설정들을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().headers().frameOptions().disable()    //h2-console 화면을 사용하기 위해 설정 disable
                .and()
                    .authorizeRequests()    //URL별 권한 관리를 설정하는 옵션의 시작점
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()  //antMatchers: 권한 관리 대상을 지정하는 옵션
                                                                                                                   //             URL, HTTP 메소드별로 관리 가능
                                                                                                                   //permitAll: 전체 열람 권한

                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())                                //hasRole:USER 권한을 가진 사람만 열람 가능
                    .anyRequest().authenticated()                                                                  //anyRequest: 나머지 URL
                                                                                                                   //authenticated: 인증된 사용자(로그인한 사용자)들에게만 허용
                .and()
                    .logout()   //로그아웃 기능에 대한 설정 시작점
                        .logoutSuccessUrl("/")  //로그아웃 성공 시 "/"로 이동
                .and()
                    .oauth2Login()  //OAuth2 로그인 기능에 대한 설정 시작점
                        .userInfoEndpoint() //OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정
                            .userService(customOAuth2UserService);  //로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체 등록
    }

}
