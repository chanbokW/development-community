package me.snsservice.comment.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.snsservice.comment.domain.Comment;
import me.snsservice.common.NoOffsetPageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

import static me.snsservice.comment.domain.QComment.comment;
import static me.snsservice.member.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository {

    private final JPAQueryFactory query;


    @Override
    public List<Comment> findAllCommentsByArticleId(Long articleId, NoOffsetPageRequest noOffsetPageRequest) {
        return query.selectFrom(comment)
                .join(comment.member, member)
                .where(
                        eqArticleId(articleId),
                        comment.id.lt(noOffsetPageRequest.getCurrent())
                )
                .orderBy(comment.id.desc())
                .limit(noOffsetPageRequest.getSize())
                .fetch();
    }

    private BooleanExpression eqArticleId(Long articleId) {
        return comment.article.id.eq(articleId);
    }
}
