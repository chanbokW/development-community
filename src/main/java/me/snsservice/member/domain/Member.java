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
    public Member(String email, String nickname, String password, Role role) {
        this.email = new Email(email);
        this.nickname = new Nickname(nickname);
        this.password = Password.from(password);
        this.role = role;
    }

    public Member(String email, String nickname, String password) {
        this.email = new Email(email);
        this.nickname = new Nickname(nickname);
        this.password = Password.from(password);
        this.role = Role.ROLE_MEMBER;
    }

    // Todo Embeddable 생성해서 관리하기
    public Member passwordEncode(PasswordEncoder encoder) {
        this.password = Password.encrypt(this.password.value(), encoder);
        return this;
    }

    public void update(String nickname, String password, PasswordEncoder encoder) {
        if (nickname != null) {
            this.nickname = new Nickname(nickname);
        }

        if (password != null) {
            this.password = Password.encrypt(password, encoder);
        }
    }

    public String getEmail() {
        return email.value();
    }

    public String getPassword() {
        return password.value();
    }

    public String getNickname() {
        return nickname.value();
    }

}
