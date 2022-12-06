package me.snsservice.member.controller;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Long> createMember(@Valid @RequestBody CreateMemberRequest createMemberRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberService.createMember(createMemberRequest));
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> showMe(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(memberService.findOneMember(userDetails.getUsername()));
    }

    @PutMapping
    public ResponseEntity<MemberResponse> updateMember(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateMemberRequest updateMemberRequest) {
        return ResponseEntity.ok()
                .body(memberService.updateMember(userDetails.getUsername(), updateMemberRequest));
    }
}

