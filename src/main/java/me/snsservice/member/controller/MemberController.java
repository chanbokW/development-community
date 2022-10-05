package me.snsservice.member.controller;

import lombok.RequiredArgsConstructor;
import me.snsservice.member.dto.MemberCreateRequest;
import me.snsservice.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Long> createMember(@Valid @RequestBody MemberCreateRequest memberCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(memberService.createMember(memberCreateRequest.toEntity()));
    }

    @GetMapping("/me")
    public ResponseEntity<Long> showMe(Long memberId) {
        return ResponseEntity.ok().body(memberId);
    }
}

