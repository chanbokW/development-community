package me.snsservice.article.repository;

import me.snsservice.article.domain.Article;
import me.snsservice.article.dto.ArticleListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ArticleCustomRepository {
    Optional<Article> findByIdWithAll(Long id);

    Page<ArticleListResponse> findAllWithArticle(Pageable pageable);

    List<Article> findAllArticles();
}
