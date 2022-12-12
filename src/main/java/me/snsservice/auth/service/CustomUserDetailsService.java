package me.snsservice.auth.service;

import lombok.RequiredArgsConstructor;
import me.snsservice.auth.domain.CustomUserDetails;
import me.snsservice.common.exception.BusinessException;
import me.snsservice.member.domain.Email;
import me.snsservice.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static me.snsservice.common.exception.ErrorCode.NOT_FOUND_MEMBER;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByEmail(new Email(username))
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_MEMBER));

    }
//
//    private User createUser(Member member) {
//        List<GrantedAuthority> authorities = Collections.singletonList(
//                new SimpleGrantedAuthority(member.getRole().toString())
//        );
//        return new User(member.getEmail(), member.getPassword(), authorities);
//    }
}
