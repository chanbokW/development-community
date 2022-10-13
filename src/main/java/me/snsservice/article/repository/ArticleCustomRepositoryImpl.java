package me.snsservice.article.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import me.snsservice.article.domain.Article;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static me.snsservice.article.domain.QArticle.article;
import static me.snsservice.article.domain.QArticleTag.articleTag;
import static me.snsservice.like.domain.QArticleLike.articleLike;
import static me.snsservice.member.domain.QMember.member;

@Repository
public class ArticleCustomRepositoryImpl implements ArticleCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ArticleCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Optional<Article> findByIdWithAll(Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectDistinct(article)
                .from(article)
                .join(article.member, member).fetchJoin()
                .leftJoin(article.articleTags, articleTag).fetchJoin()
                .leftJoin(articleTag.tag).fetchJoin()
                .where(article.id.eq(id))
                .fetchOne());
    }
}
