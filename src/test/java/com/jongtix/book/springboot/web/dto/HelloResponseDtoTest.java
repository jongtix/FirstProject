package com.jongtix.book.springboot.web.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        //then
        assertThat(dto.getName()).isEqualTo(name);  //assertThat: assertj라는 테스트 검증 라이브러리의 검증 메소드
        assertThat(dto.getAmount()).isEqualTo(amount);
        /*
        * assertj의 assertThat VS Junit의 assertThat
        * CoreMatchers와 같은 별도 라이브러리가 추가로 필요하지 않음
        * IDE에서 좀 더 확실한 자동완성이 지원
        * */
    }
}
