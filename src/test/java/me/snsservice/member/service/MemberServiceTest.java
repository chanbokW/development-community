package me.snsservice.member.service;

import me.snsservice.common.error.exception.BusinessException;
import me.snsservice.member.domain.Member;
import me.snsservice.member.dto.CreateMemberRequest;
import me.snsservice.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입을 한다 - 정상흐름")
    void createMemberTest() {
        //        return new CreateMemberRequest("test1234@naver.com", "test1234", "hello");
        CreateMemberRequest request = createMemberRequest("test1234@naver.com","test1234@", "hello");

        Long saveId = memberService.createMember(request);

        assertThat(saveId).isNotNull();
    }

    @Test
    @DisplayName("중복된 이메일로 회원가입시도시 예외가 발생한다")
    void emailValidationExceptionTest() {
        Member member = getMember("hello@gmail.com", "pwpw001!", "hello");
        memberRepository.save(member);

        CreateMemberRequest memberRequest = createMemberRequest("hello@gmail.com", "pwpw001!", "hello");
        assertThrows(BusinessException.class, () -> memberService.createMember(memberRequest));
    }

    @Test
    @DisplayName("중복된 닉네임으로 회원가입시도시 예외가 발생한다")
    void nicknameValidationExceptionTest() {
        Member member = getMember("hello1234@gmail.com", "pwpw0011@", "hello");
        memberRepository.save(member);
//
//        Member member2 = Member.builder()
//                .email("spring@gmail.com")
//                .password("pw001")
//                .nickname("spring")
//                .build();
        CreateMemberRequest memberRequest = createMemberRequest("hello12345@gmail.com", "pwpw0011@", "hello");
        assertThrows(BusinessException.class, () -> memberService.createMember(memberRequest));
    }
    private Member getMember() {
        return Member.builder()
                .email("hello@gmail.com")
                .password("pwpw001!")
                .nickname("spring")
                .build();
    }

    private Member getMember(String email, String password, String nickname) {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }

    private CreateMemberRequest createMemberRequest(String email, String password, String nickname) {
        return new CreateMemberRequest(email, password, nickname);
    }
}