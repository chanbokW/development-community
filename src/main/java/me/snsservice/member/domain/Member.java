package me.snsservice.member.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.snsservice.common.domain.BaseTimeEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;


    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String email, String nickname, String password, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

    // Todo Embeddable 생성해서 관리하기
    public Member passwordEncord(PasswordEncoder encoder) {
        this.password = encoder.encode(this.password);
        return this;
    }
}
