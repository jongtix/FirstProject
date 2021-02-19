package com.jongtix.book.springboot.domain.user;

import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

//Junit4
//@RunWith(SpringRunner.class)
//Junit5
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    //Junit4
    //@After
    //Junit5
    @AfterEach
    public void cleanup() { userRepository.deleteAll(); }

    @Test
    public void 이메일로_사용자_찾기() {
        //given
        String name = "name";
        String email = "test@test.com";
        String picture = "picture";
        Role role = Role.GUEST;

        userRepository.save(User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(role)
                .build());

        //when
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. email : " + email));

        //then
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPicture()).isEqualTo(picture);
        assertThat(user.getRole()).isEqualTo(role);
    }

}
