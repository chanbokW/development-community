package me.snsservice.article.service;

import lombok.RequiredArgsConstructor;
import me.snsservice.article.domain.Article;
import me.snsservice.article.dto.ArticleRequest;
import me.snsservice.article.repository.ArticleRepository;
import me.snsservice.member.domain.Member;
import me.snsservice.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createArticle(ArticleRequest articleRequest, Long loginMember) {
        Member member = getMember(loginMember);
        Article article = articleRepository.save(articleRequest.toEntity(member));

        //Todo 태그 저장 기능추가

        return article.getId();
    }

    private Member getMember(Long loginMember) {
        return memberRepository.findById(loginMember)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 회원입니다."));
    }

}
