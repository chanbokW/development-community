package me.snsservice.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.snsservice.member.domain.Member;
import me.snsservice.member.domain.Role;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponse {
    private Long id;
    private String email;
    private String nickname;
    private Role role;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime createAt;

    public static MemberResponse of(Member member) {
        return new MemberResponse(
                member.getId(), member.getEmail(), member.getNickname(), member.getRole(), member.getCreateDate());
    }
}
