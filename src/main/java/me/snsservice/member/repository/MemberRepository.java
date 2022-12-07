package me.snsservice.member.repository;

import me.snsservice.member.domain.Email;
import me.snsservice.member.domain.Member;
import me.snsservice.member.domain.Nickname;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(Email email);
    boolean existsByEmail(Email email);
    boolean existsByNickname(Nickname nickname);
}
