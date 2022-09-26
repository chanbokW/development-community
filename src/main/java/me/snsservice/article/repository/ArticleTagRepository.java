package me.snsservice.article.repository;

import me.snsservice.article.domain.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
}
