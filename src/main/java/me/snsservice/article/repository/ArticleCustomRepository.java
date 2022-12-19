package me.snsservice.article.repository;

import me.snsservice.article.controller.ArticleOptionType;
import me.snsservice.article.controller.ArticleSearchOption;
import me.snsservice.article.domain.Article;
import me.snsservice.article.dto.ArticleListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ArticleCustomRepository {
    Optional<Article> findByIdWithAll(Long id);

//    Page<ArticleListResponse> findAllByKeyword(ArticleSearchOption articleSearchOption, Pageable pageable);

    Page<Article> findAllByKeyword(String keyword, ArticleOptionType optionType, Pageable pageable);
    List<Article> findAllArticles();

    List<Long> findAllArticleIdsByTagNames(List<String> search);
}
