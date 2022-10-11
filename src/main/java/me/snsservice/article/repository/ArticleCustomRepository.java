package me.snsservice.article.repository;

import me.snsservice.article.dto.ArticleResponse;

import java.util.Optional;

public interface ArticleCustomRepository {
    Optional<ArticleResponse> findArticleDetail(Long articleId);
    // Todo 전체게시물 response
}
