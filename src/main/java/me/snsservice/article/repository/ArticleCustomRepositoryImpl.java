package me.snsservice.article.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import me.snsservice.article.controller.ArticleOptionType;
import me.snsservice.article.controller.ArticleSearchOption;
import me.snsservice.article.domain.Article;
import me.snsservice.article.dto.ArticleListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.core.types.dsl.Wildcard.count;
import static me.snsservice.article.domain.QArticle.article;
import static me.snsservice.article.domain.QArticleTag.articleTag;
import static me.snsservice.member.domain.QMember.member;
import static me.snsservice.tag.domain.QTag.tag;

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
//
//    @Override
//    public Page<ArticleListResponse> findAllByKeyword(ArticleSearchOption articleSearchOption, Pageable pageable) {
//        List<ArticleListResponse> fetch = query.select(constructor(
//                                ArticleListResponse.class,
//                                article,
//                                member.nickname.nickname
//                        )
//                )
//                .from(article, article)
//                .join(article.member, member)
//                .where(eqActivated(true),
//                        searchByKeyword(articleSearchOption.getKeyword(),articleSearchOption.getOptionType()))
//                .limit(pageable.getPageSize())
//                .offset(pageable.getOffset())
//                .orderBy(article.id.desc())
//                .fetch();
//
//        JPAQuery<Long> count = query.select(article.count())
//                .from(article)
//                .where(article.activated.eq(true));
//        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchOne);
//    }

    @Override
    public Page<Article> findAllByKeyword(String keyword, ArticleOptionType optionType, Pageable pageable) {
        List<Article> fetch = query.selectFrom(article)
                .join(article.member, member).fetchJoin()
                .where(
                        searchByKeyword(keyword, optionType)
                )
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(article.id.desc())
                .fetch();

        JPAQuery<Long> count = query.select(article.count())
                .from(article)
                .where(eqActivated(true));
        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchOne);
    }

    private BooleanExpression eqActivated(boolean isStatus) {
        return article.activated.eq(isStatus);
    }

    private BooleanExpression searchByKeyword(String keyword, ArticleOptionType optionType) {
        if (keyword == null || optionType == null || optionType == ArticleOptionType.ALL) {
            return null;
        }

        switch (optionType) {
            case TITLE:
                return article.title.title.like("%" + keyword + "%");
            case CONTENT:
                return article.content.content.like("%" + keyword + "%");
            case TITLE_CONTENT:
                return article.title.title.like("%" + keyword + "%")
                        .or(article.content.content.like("%" + keyword + "%"));
            default:
                return null;
        }
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

    public List<Long> findAllArticleIdsByTagNames(List<String> tagNames) {
        return query.selectDistinct(article.id)
                .from(article)
                .join(article.articleTags, articleTag)
                .join(articleTag.tag, tag)
                .where(
                        searchByTagNames(tagNames)
                ).fetch();
    }

    private BooleanExpression searchByTagNames(List<String> tagNames) {
        return tagNames == null ? null : tag.name.in(tagNames);

    }

}
