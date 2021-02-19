package com.jongtix.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing    //JPA Auditing 활성화
                        //HelloControllerTest에서 발생하는 ERROR를 해결하기 위해 JpaConfig로 이동
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
