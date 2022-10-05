package me.snsservice.member.service;

import lombok.RequiredArgsConstructor;
import me.snsservice.member.domain.Member;
import me.snsservice.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public Long createMember(Member newMember) {
        existByEmail(newMember.getEmail());
        existByNickname(newMember.getNickname());

        Member saveMember = memberRepository.save(newMember.passwordEncord(encoder));
        return saveMember.getId();
    }

    private void existByEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 존재한 이매일입니다.");
        }
    }

    private void existByNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("이미 존재한 닉네임입니다.");
        }
    }
}
