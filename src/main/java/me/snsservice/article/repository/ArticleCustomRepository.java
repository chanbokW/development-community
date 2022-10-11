package me.snsservice.article.repository;

import java.util.Optional;

public interface ArticleCustomRepository {
    Optional<ArticleQueryDto> findByArticleId(Long articleId);
    // Todo 전체게시물 response
}
