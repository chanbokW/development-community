package me.snsservice.like.service;

import lombok.RequiredArgsConstructor;
import me.snsservice.article.domain.Article;
import me.snsservice.article.repository.ArticleRepository;
import me.snsservice.like.domain.ArticleLike;
import me.snsservice.like.repository.ArticleLikeRepository;
import me.snsservice.member.domain.Member;
import me.snsservice.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {

    private final ArticleLikeRepository articleLikeRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void like(Long articleId, Long loginId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시물을 찾을 수 없습니다"));
        //Todo
        Member member = memberRepository.findById(loginId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자는 좋아요 누를 권한이 없습니다."));

        if(!articleLikeRepository.findByArticleAndMember(article,member).isPresent()) {
            articleLikeRepository.save(new ArticleLike(article, member));
        }
    }

    @Transactional
    public void unlike(Long articleId, Long loginId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("해당 게시물을 찾을 수 없습니다"));
        //Todo
        Member member = memberRepository.findById(loginId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자는 좋아요 누를 권한이 없습니다."));

        articleLikeRepository.findByArticleAndMember(article, member)
                .ifPresent(articleLike -> {
                    articleLikeRepository.delete(articleLike);
                    article.getLikes().remove(articleLike);
                });
    }
}
