package me.snsservice.auth.domain;

import lombok.Getter;
import me.snsservice.member.domain.Member;
import me.snsservice.member.domain.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.List;

@Getter
public class CustomUserDetails extends User {
    private final Long id;
    private final String email;
    private final List<String> authority;

    public CustomUserDetails(Member member) {
        super(member.getEmail(), member.getPassword(), Collections.singletonList(
                new SimpleGrantedAuthority(member.getRole().toString())
        ));
        this.id = member.getId();
        this.email = member.getEmail();
        this.authority = List.of(member.getRole().toString());
    }

    public CustomUserDetails(Long id, String email, String role) {
        super(email, "", List.of(new SimpleGrantedAuthority(role)));
        this.id = id;
        this.email = email;
        this.authority = List.of(role);
    }

    public CustomUserDetails(Long id, String email, Role role) {
        super(email, "", List.of(new SimpleGrantedAuthority(role.toString())));
        this.id = id;
        this.email = email;
        this.authority = List.of(role.toString());
    }
}
