package me.snsservice.member.service;

import lombok.RequiredArgsConstructor;
import me.snsservice.common.exception.BusinessException;
import me.snsservice.member.domain.Email;
import me.snsservice.member.domain.Member;
import me.snsservice.member.domain.Nickname;
import me.snsservice.member.dto.CreateMemberRequest;
import me.snsservice.member.dto.MemberResponse;
import me.snsservice.member.dto.UpdateMemberRequest;
import me.snsservice.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static me.snsservice.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public Long createMember(CreateMemberRequest createMemberRequest) {
        existByEmail(createMemberRequest.getEmail());
        existByNickname(createMemberRequest.getNickname());

        Member saveMember = memberRepository
                .save(createMemberRequest.toEntity().passwordEncode( encoder));
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
        String password = updateMemberRequest.getPassword();
        if (!findMember.getNickname().equals(nickname)) {
            existByNickname(nickname);
        }
        findMember.update(nickname, password, encoder);
        return MemberResponse.of(findMember);
    }

    @Transactional
    public void deleteMember(String email) {
        Member findMember = getMemberByEmail(email);
        memberRepository.delete(findMember);
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new BusinessException(NOT_FOUND_MEMBER));

    }

    private void existByEmail(String email) {
        if (memberRepository.existsByEmail(new Email(email))) {
            throw new BusinessException(EXISTS_EMAIL);
        }
    }

    private void existByNickname(String nickname) {
        if (memberRepository.existsByNickname(new Nickname(nickname))) {
            throw new BusinessException(EXISTS_NICKNAME);
        }
    }
}
