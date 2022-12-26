package me.snsservice.comment.controller;

import lombok.RequiredArgsConstructor;
import me.snsservice.auth.controller.Login;
import me.snsservice.auth.controller.LoginMember;
import me.snsservice.comment.dto.CommentResponse;
import me.snsservice.comment.dto.CreateCommentRequest;
import me.snsservice.comment.dto.UpdateCommentRequest;
import me.snsservice.comment.service.CommentService;
import me.snsservice.common.NoOffsetPageRequest;
import me.snsservice.common.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommonResponse<Long> createComment(
            @Login LoginMember loginMember,
            @Valid @RequestBody CreateCommentRequest createCommentRequest
    ) {
        Long commnetId = commentService.createComment(createCommentRequest, loginMember.getId());
        return CommonResponse.res(HttpStatus.CREATED, "댓글 생성", commnetId);
    }

    /**
     *
     * @param articleId 게시물 id
     * @param currentCommnetId 마지막  댓글 아이디
     * @param size default 10개
     * @return List commentResponse
     *
     * 댓글 최신 순 기준
     * @TODO 댓글 정렬
     */
    @GetMapping("/{articleId}")
    public CommonResponse<List<CommentResponse>> findAllCommentByArticle(
            @PathVariable Long articleId,
            @RequestParam(name = "current", required = false) Long currentCommnetId,
            @RequestParam(defaultValue = "10") int size
    ) {
        NoOffsetPageRequest noOffsetPageRequest = NoOffsetPageRequest.of(currentCommnetId, size);
        List<CommentResponse> responses = commentService.findAllCommentsByArticleId(articleId, noOffsetPageRequest);
        return CommonResponse.res(HttpStatus.OK, "게시물 댓글 조회", responses);
    }

    @PatchMapping("/{commnetId}")
    public CommonResponse<Void> updateComment(
            @PathVariable Long commentId,
            @Login LoginMember loginMember,
            @Valid @RequestBody UpdateCommentRequest updateCommentRequest
    ) {
        commentService.updateComment(updateCommentRequest, commentId, loginMember.getId());
        return CommonResponse.res(HttpStatus.OK, "댓글 수정");
    }

    @DeleteMapping("/{commnetId}")
    public CommonResponse<Void> deleteComment(
            @PathVariable Long commentId,
            @Login LoginMember loginMember
    ) {
        commentService.deleteComment(commentId, loginMember.getId());
        return CommonResponse.res(HttpStatus.NO_CONTENT, "댓글 삭제");
    }
}
