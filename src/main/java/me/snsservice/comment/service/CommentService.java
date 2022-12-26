package me.snsservice.comment.service;

import lombok.RequiredArgsConstructor;
import me.snsservice.article.domain.Article;
import me.snsservice.article.repository.ArticleRepository;
import me.snsservice.comment.domain.Comment;
import me.snsservice.comment.dto.CommentResponse;
import me.snsservice.comment.dto.CreateCommentRequest;
import me.snsservice.comment.dto.UpdateCommentRequest;
import me.snsservice.comment.repository.CommentRepository;
import me.snsservice.common.NoOffsetPageRequest;
import me.snsservice.common.exception.BusinessException;
import me.snsservice.member.domain.Member;
import me.snsservice.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static me.snsservice.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createComment(CreateCommentRequest request, Long loginId) {
        Article article = articleRepository.findById(request.getArticleId())
                .orElseThrow(() -> new BusinessException(NOT_FOUND_ARTICLE));
        Member member = memberRepository.findById(loginId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_MEMBER));
        Comment comment = new Comment(member, article, request.getContent());
        return commentRepository
                .save(comment)
                .getId();
    }

    public List<CommentResponse> findAllCommentsByArticleId(Long articleId, NoOffsetPageRequest noOffsetPageRequest) {
        List<Comment> comments = commentRepository.findAllCommentsByArticleId(articleId, noOffsetPageRequest);
        return comments.stream()
                .map(CommentResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateComment(UpdateCommentRequest request, Long commnetId, Long loginId) {
        Comment comment = commentRepository.findById(commnetId)
                .orElseThrow();

        if (!comment.getMember().getId().equals(loginId)) {
            throw new BusinessException(UNAUTHORIZED_COMMENT_MEMBER);
        }

        comment.updateCommnet(request.getContent());
    }

    @Transactional
    public void deleteComment(Long commentId, Long loginId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(NOT_FOUND_COMMENT));

        if (!comment.getMember().getId().equals(loginId)) {
            throw new BusinessException(UNAUTHORIZED_COMMENT_MEMBER);
        }
        commentRepository.delete(comment);
    }
}
