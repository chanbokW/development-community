package me.snsservice.article.repository;

import lombok.RequiredArgsConstructor;
import me.snsservice.article.domain.Article;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleCustomJpqlRepository {

    private final EntityManager em;

    public Optional<Article> findByIdWithArticleAndArticleTagAndTagAndLike(Long articleId) {
        try {
            Article article = em.createQuery("select distinct article from Article article " +
                            "left join fetch article.articleTags articleTags " +
                            "left join fetch articleTags.tag tag " +
                            "join fetch article.member member " +
                            "where article.id = :id", Article.class)
                    .setParameter("id", articleId)
                    .getSingleResult();
            return Optional.ofNullable(article);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
