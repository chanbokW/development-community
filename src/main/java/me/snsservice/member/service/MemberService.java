package me.snsservice.member.service;

import lombok.RequiredArgsConstructor;
import me.snsservice.common.error.exception.BusinessException;
import me.snsservice.member.domain.Member;
import me.snsservice.member.dto.MemberResponse;
import me.snsservice.member.dto.UpdateMemberRequest;
import me.snsservice.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static me.snsservice.common.error.ErrorCode.*;

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

    @Transactional(readOnly = true)
    public MemberResponse findOneMember(String email) {
        Member findMember = getMemberByEmail(email);
        return MemberResponse.of(findMember);
    }

    @Transactional
    public MemberResponse updateMember(String email, UpdateMemberRequest updateMemberRequest) {
        Member findMember = getMemberByEmail(email);
        String nickname = updateMemberRequest.getNickname();
        if (!findMember.getNickname().equals(nickname)) {
            existByNickname(nickname);
        }
        findMember.update(updateMemberRequest.toEntity(), encoder);

        return MemberResponse.of(findMember);
    }

    @Transactional
    public void deleteMember(String email) {
        Member findMember = getMemberByEmail(email);
        memberRepository.delete(findMember);
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_MEMBER));

    }

    private void existByEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BusinessException(EXISTS_EMAIL);
        }
    }

    private void existByNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new BusinessException(EXISTS_NICKNAME);
        }
    }
}
