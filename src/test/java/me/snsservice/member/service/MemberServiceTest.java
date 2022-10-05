package me.snsservice.member.service;

import me.snsservice.member.domain.Member;
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
        Member member = getMember();

        Long saveId = memberService.createMember(member);

        assertThat(saveId).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("중복된 이메일로 회원가입시도시 예외가 발생한다")
    void emailValidationExceptionTest() {
        Member member = getMember();
        memberRepository.save(member);

        Member member2 = Member.builder()
                .email("hello@gmail.com")
                .password("pw001")
                .nickname("hello")
                .build();

        assertThrows(IllegalArgumentException.class, () -> memberService.createMember(member2));
    }

    @Test
    @DisplayName("중복된 이메일로 회원가입시도시 예외가 발생한다")
    void nicknameValidationExceptionTest() {
        Member member = getMember();
        memberRepository.save(member);

        Member member2 = Member.builder()
                .email("spring@gmail.com")
                .password("pw001")
                .nickname("spring")
                .build();

        assertThrows(IllegalArgumentException.class, () -> memberService.createMember(member2));
    }
    private Member getMember() {
        return Member.builder()
                .email("hello@gmail.com")
                .password("pw001")
                .nickname("spring")
                .build();
    }
}