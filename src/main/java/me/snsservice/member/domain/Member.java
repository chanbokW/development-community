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

    @Embedded
    private Email email;

    @Embedded
    private Nickname nickname;

    @Embedded
    private Password password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String email, String nickname, String password,Role role) {
        this.email = new Email(email);
        this.nickname = new Nickname(nickname);
        this.password = Password.from(password);
        this.role = role;
    }

    // Todo Embeddable 생성해서 관리하기
    public Member passwordEncode(PasswordEncoder encoder) {
        this.password = Password.encrypt(this.password.value(), encoder);
        return this;
    }

    public void update(Member updateMember, PasswordEncoder encoder) {
        if (updateMember.getNickname() != null) {
            this.nickname = new Nickname(updateMember.getNickname());
        }

        if (updateMember.getPassword() != null) {
            this.password = Password.encrypt(updateMember.getPassword(), encoder);
        }
    }

    public String getEmail() {
        return email.value();
    }

    public String getPassword() {
        return password.value();
    }

    public String getNickname() {
        return password.value();
    }

}
