package me.snsservice.article.repository;

import me.snsservice.article.controller.ArticleOptionType;
import me.snsservice.article.domain.Article;
import me.snsservice.common.NoOffsetPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ArticleCustomRepository {
    Optional<Article> findByIdWithAll(Long id);

//    Page<ArticleListResponse> findAllByKeyword(ArticleSearchOption articleSearchOption, Pageable pageable);

    List<Article> findAllArticlesByKeyword(String keyword, ArticleOptionType optionType, NoOffsetPageRequest noOffsetPageRequest);
    List<Article> findAllArticles();

    List<Long> findAllArticleIdsByTagNames(List<String> search, Pageable pageable);
}
