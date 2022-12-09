package me.snsservice.article.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import me.snsservice.article.domain.Article;
import me.snsservice.article.dto.ArticleListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Projections.constructor;
import static me.snsservice.article.domain.QArticle.article;
import static me.snsservice.article.domain.QArticleTag.articleTag;
import static me.snsservice.member.domain.QMember.member;

@Repository
public class ArticleCustomRepositoryImpl implements ArticleCustomRepository {

    private final JPAQueryFactory query;

    public ArticleCustomRepositoryImpl(JPAQueryFactory query) {
        this.query = query;
    }

    @Override
    public Optional<Article> findByIdWithAll(Long id) {
        return Optional.ofNullable(query.selectDistinct(article)
                .from(article)
                .join(article.member, member).fetchJoin()
                .leftJoin(article.articleTags, articleTag).fetchJoin()
                .leftJoin(articleTag.tag).fetchJoin()
                .where(article.id.eq(id))
                .where(article.activated.eq(true))
                .fetchOne());
    }

    @Override
    public Page<ArticleListResponse> findAllWithArticle(Pageable pageable) {
        List<ArticleListResponse> fetch = query.select(constructor(
                                ArticleListResponse.class,
                                article,
                                member.nickname.nickname
                        )
                )
                .from(article, article)
                .join(article.member, member)
                .where(article.activated.eq(true))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query.select(article.count())
                .from(article)
                .where(article.activated.eq(true));
        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchOne);
    }

    @Override
    public List<Article> findAllArticles() {
        return query.selectDistinct(article)
                .from(article)
                .join(article.member, member).fetchJoin()
                .leftJoin(article.articleTags, articleTag).fetchJoin()
                .leftJoin(articleTag.tag).fetchJoin()
                .fetch();
    }

    private BooleanExpression searchByTag(String tag) {
        return null;
    }
}
