package me.snsservice.article.repository;

import me.snsservice.article.domain.Article;

import java.util.Optional;

public interface ArticleCustomRepository {
    Optional<Article> findByIdWithAll(Long id);
}
