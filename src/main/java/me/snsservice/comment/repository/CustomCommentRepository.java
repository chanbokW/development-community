package me.snsservice.comment.repository;

import me.snsservice.comment.domain.Comment;
import me.snsservice.common.NoOffsetPageRequest;

import java.util.List;

public interface CustomCommentRepository {
    List<Comment> findAllCommentsByArticleId(Long articleId, NoOffsetPageRequest noOffsetPageRequest);
}
