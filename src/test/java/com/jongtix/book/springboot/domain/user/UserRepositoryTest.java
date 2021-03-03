package com.jongtix.book.springboot.domain.user;

import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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

    @DisplayName("querydsl_내림차순_정렬해서_사용자_찾기")
    @Test
    public void querydsl_find_user_order_by_desc() {
        //given
        userRepository.save(User.builder()
                .name("name1")
                .email("email3")
                .picture("picture1")
                .role(Role.GUEST)
                .build());

        userRepository.save(User.builder()
                .name("name2")
                .email("email2")
                .picture("picture3")
                .role(Role.GUEST)
                .build());

        userRepository.save(User.builder()
                .name("name3")
                .email("email1")
                .picture("picture2")
                .role(Role.GUEST)
                .build());

        //when
        List<User> userListOrderByNameDesc = userRepository.findAllOrderByDesc("name");
        List<User> userListOrderByEmailDesc = userRepository.findAllOrderByDesc("email");
        List<User> userListOrderByPictureDesc = userRepository.findAllOrderByDesc("picture");

        //then
        assertThat(userListOrderByNameDesc.get(0).getName()).isEqualTo("name3");
        assertThat(userListOrderByNameDesc.get(1).getName()).isEqualTo("name2");
        assertThat(userListOrderByNameDesc.get(2).getName()).isEqualTo("name1");
        assertThat(userListOrderByEmailDesc.get(0).getName()).isEqualTo("name1");
        assertThat(userListOrderByEmailDesc.get(1).getName()).isEqualTo("name2");
        assertThat(userListOrderByEmailDesc.get(2).getName()).isEqualTo("name3");
        assertThat(userListOrderByPictureDesc.get(0).getName()).isEqualTo("name2");
        assertThat(userListOrderByPictureDesc.get(1).getName()).isEqualTo("name3");
        assertThat(userListOrderByPictureDesc.get(2).getName()).isEqualTo("name1");
    }

    @DisplayName("querydsl_오름차순_정렬해서_사용자_찾기")
    @Test
    public void querydsl_find_user_order_by() {
        //given
        userRepository.save(User.builder()
                .name("name1")
                .email("email3")
                .picture("picture1")
                .role(Role.GUEST)
                .build());

        userRepository.save(User.builder()
                .name("name2")
                .email("email2")
                .picture("picture3")
                .role(Role.GUEST)
                .build());

        userRepository.save(User.builder()
                .name("name3")
                .email("email1")
                .picture("picture2")
                .role(Role.GUEST)
                .build());

        //when
        List<User> userListOrderByName = userRepository.findAllOrderBy("name");
        List<User> userListOrderByEmail = userRepository.findAllOrderBy("email");
        List<User> userListOrderByPicture = userRepository.findAllOrderBy("picture");

        //then
        assertThat(userListOrderByName.get(0).getName()).isEqualTo("name1");
        assertThat(userListOrderByName.get(1).getName()).isEqualTo("name2");
        assertThat(userListOrderByName.get(2).getName()).isEqualTo("name3");
        assertThat(userListOrderByEmail.get(0).getName()).isEqualTo("name3");
        assertThat(userListOrderByEmail.get(1).getName()).isEqualTo("name2");
        assertThat(userListOrderByEmail.get(2).getName()).isEqualTo("name1");
        assertThat(userListOrderByPicture.get(0).getName()).isEqualTo("name1");
        assertThat(userListOrderByPicture.get(1).getName()).isEqualTo("name3");
        assertThat(userListOrderByPicture.get(2).getName()).isEqualTo("name2");
    }

    @DisplayName("querydsl_이름으로_사용자_찾기")
    @Test
    public void querydsl_find_user_by_name() {
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
        List<User> userList = userRepository.findByName(name);

        //then
        assertThat(userList.get(0).getName()).isEqualTo(name);
        assertThat(userList.get(0).getEmail()).isEqualTo(email);
        assertThat(userList.get(0).getPicture()).isEqualTo(picture);
        assertThat(userList.get(0).getRole()).isEqualTo(role);
    }

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
