package me.snsservice.article.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleCustomJpqlRepository implements ArticleCustomRepository {

    private final EntityManager em;

    @Override
    public Optional<ArticleQueryDto> findByArticleId(Long articleId) {
        try {
            ArticleQueryDto articleQueryDto = em.createQuery(
                            "SELECT " +
                                    "new me.snsservice.article.repository.ArticleQueryDto(a.id, a.title, a.content, a.viewCount, m.nickname, a.likes.size) " +
                                    "from Article a " +
                                    "join a.member m " +
                                    "where a.id = :articleId", ArticleQueryDto.class)
                    .setParameter("articleId", articleId)
                    .getSingleResult();
            return Optional.ofNullable(articleQueryDto);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<String> findTagsByArticleId(Long articleId) {
        return em.createQuery("select " +
                "t.name " +
                "from ArticleTag ts " +
                        "join ts.tag t " +
                "where ts.article.id = :id", String.class)
                .setParameter("id", articleId)
                .getResultList();
    }
}
