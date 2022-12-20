package me.snsservice.member.controller;

import lombok.RequiredArgsConstructor;
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
        return CommonResponse
                .res(HttpStatus.CREATED, "회원가입", memberService.createMember(createMemberRequest));
    }

    @GetMapping("/me")
    public CommonResponse<MemberResponse> showMe(@AuthenticationPrincipal UserDetails userDetails) {
        return CommonResponse.res(HttpStatus.OK, "마이페이지 조회", memberService.findOneMember(userDetails.getUsername()));
    }

    @PutMapping
    public CommonResponse<MemberResponse> updateMember(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateMemberRequest updateMemberRequest) {
        return CommonResponse
                .res(HttpStatus.OK, "회원 수정", memberService.updateMember(userDetails.getUsername(), updateMemberRequest));
    }
}

