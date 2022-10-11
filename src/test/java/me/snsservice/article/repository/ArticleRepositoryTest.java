package me.snsservice.article.repository;

import me.snsservice.article.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class ArticleRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("게시물 하나 조회 조인을통해 데이터 가져오는 테스트를 실행한다.")
    void findOneArticleQueryTest() {
        Optional<ArticleQueryDto> article;
        try {
            ArticleQueryDto result = em.createQuery(
                            "SELECT " +
                                    "new me.snsservice.article.repository.ArticleQueryDto(a.id, a.title, a.content, a.viewCount, m.nickname, a.likes.size) " +
                                    "from Article a " +
                                    "join a.member m " +
                                    "where a.id = :articleId", ArticleQueryDto.class)
                    .setParameter("articleId", 1L)
                    .getSingleResult();
            article = Optional.ofNullable(result);
        } catch (Exception e) {
            article = Optional.empty();
        }
        System.out.println(article);
    }

    @Test
    @DisplayName("게시물아이디로 의 태그를 가지고 오는 테스트")
    void articleTagsTest() {
        List<ArticleTagQueryDto> articleTags = em.createQuery("select" +
                        " new me.snsservice.article.repository.ArticleTagQueryDto(ts.article.id, t.name) " +
                        "from ArticleTag ts " +
                        "join ts.tag t " +
                        "where ts.article.id = :id", ArticleTagQueryDto.class)
                .setParameter("id", 1L)
                .getResultList();

        System.out.println("articleTags = " + articleTags);


    }
}