package me.snsservice.like.service;

import lombok.RequiredArgsConstructor;
import me.snsservice.article.domain.Article;
import me.snsservice.article.repository.ArticleRepository;
import me.snsservice.common.exception.BusinessException;
import me.snsservice.like.domain.ArticleLike;
import me.snsservice.like.dto.ArticleLIkeStatusResponse;
import me.snsservice.like.repository.ArticleLikeRepository;
import me.snsservice.member.domain.Member;
import me.snsservice.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static me.snsservice.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {

    private final ArticleLikeRepository articleLikeRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void like(Article article, Member member) {
        articleLikeRepository.save(new ArticleLike(article, member));
    }

    @Transactional(readOnly = true)
    public ArticleLIkeStatusResponse getArticleAndLike(Long articleId, Member member) {
        Article article = getArticle(articleId);
        Boolean existsLike = existsLike(article, member);
        return ArticleLIkeStatusResponse.builder()
                .isLike(existsLike)
                .article(article)
                .build();
    }

    private Boolean existsLike(Article article, Member member) {
        return articleLikeRepository.findByArticleAndMember(article, member).isPresent();
    }

    private Article getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_ARTICLE));
    }

    @Transactional
    public void unlike(Article article, Member member) {
        articleLikeRepository.findByArticleAndMember(article, member)
                .ifPresent(articleLike -> {
                    articleLikeRepository.delete(articleLike);
                    article.getLikes().remove(articleLike);
                });
    }
}
