package me.snsservice.common.jwt;

import lombok.RequiredArgsConstructor;
import me.snsservice.member.domain.Member;
import me.snsservice.member.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(username)
                .map(this::createUser)
                .orElseThrow(() -> new UsernameNotFoundException(username + "존재하지 않습니다."));
    }

    private User createUser(Member member) {
        List<GrantedAuthority>  authorities = Collections.singletonList(
                new SimpleGrantedAuthority(member.getRole().toString())
        );

        return new User(member.getEmail(), member.getPassword(), authorities);
    }
}
