package me.snsservice.member.controller;

import lombok.RequiredArgsConstructor;
import me.snsservice.auth.controller.Login;
import me.snsservice.auth.controller.LoginMember;
import me.snsservice.common.response.CommonResponse;
import me.snsservice.member.dto.CreateMemberRequest;
import me.snsservice.member.dto.MemberResponse;
import me.snsservice.member.dto.UpdateMemberRequest;
import me.snsservice.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public CommonResponse<Long> createMember(@Valid @RequestBody CreateMemberRequest createMemberRequest) {
        Long memberId = memberService.createMember(createMemberRequest);
        return CommonResponse.res(HttpStatus.CREATED, "회원가입", memberId);
    }

    @GetMapping("/{id}")
    public CommonResponse<MemberResponse> findOneMember(@PathVariable Long id) {
        MemberResponse memberResponse = memberService.findOneMember(id);
        return CommonResponse.res(HttpStatus.OK, "회원 조회", memberResponse);
    }

    @PutMapping
    public CommonResponse<MemberResponse> updateMember(
            @RequestBody UpdateMemberRequest updateMemberRequest,
            @Login LoginMember loginMember
            ) {
        MemberResponse memberResponse = memberService.updateMember(loginMember.getId(), updateMemberRequest);
        return CommonResponse.res(HttpStatus.OK, "회원 수정", memberResponse);
    }

    @DeleteMapping
    public CommonResponse<Void> deleteMember(@Login LoginMember loginMember) {
        memberService.deleteMember(loginMember.getId());
        return CommonResponse.res(HttpStatus.NO_CONTENT, "회원 탈퇴");
    }
}

