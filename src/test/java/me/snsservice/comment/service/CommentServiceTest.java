package me.snsservice.comment.service;

import me.snsservice.article.domain.Article;
import me.snsservice.article.repository.ArticleRepository;
import me.snsservice.comment.domain.Comment;
import me.snsservice.comment.dto.CreateCommentRequest;
import me.snsservice.comment.repository.CommentRepository;
import me.snsservice.common.exception.BusinessException;
import me.snsservice.common.exception.ErrorCode;
import me.snsservice.member.domain.Member;
import me.snsservice.member.domain.Role;
import me.snsservice.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("댓글 생성 성공테스트")
    void createCommentTest() {
        Member member = setUpMember();

        Article article = setUpArticle(member);

        memberRepository.save(member);
        articleRepository.save(article);

        CreateCommentRequest request = createRequest(article.getId(), "good content");

        Long commentId = commentService.createComment(request, member.getId());

        assertThat(commentId).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않은 게시물에 댓글 등록시 예외가 발생")
    void createCommentNotFoundArticleExceptionTest() {
        Member member = setUpMember();
        memberRepository.save(member);
        CreateCommentRequest request = createRequest(1L, "good content");

        assertThatThrownBy(() -> commentService.createComment(request, member.getId()))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.NOT_FOUND_ARTICLE.getMessage());
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteCommentTest() {
        Member member = setUpMember();
        Article article = setUpArticle(member);
        memberRepository.save(member);
        articleRepository.save(article);

        Comment comment = new Comment(member, article, "content");
        commentRepository.save(comment);

        commentService.deleteComment(comment.getId(), member.getId());
    }

    @Test
    @DisplayName("댓글 삭제시 작성자가 아닐 경우 예외가 발생한다")
    void deleteCommentExceptionTest() {
        Member member = setUpMember();
        Article article = setUpArticle(member);
        memberRepository.save(member);
        articleRepository.save(article);

        Comment comment = new Comment(member, article, "content");
        commentRepository.save(comment);

        assertThatThrownBy(() -> commentService.deleteComment(comment.getId(), 9000000L))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.UNAUTHORIZED_COMMENT_MEMBER.getMessage());
    }


    private Member setUpMember() {
        return Member.builder()
                .email("helloqe123@test.com")
                .password("passwordtest1!@")
                .nickname("jpa")
                .role(Role.ROLE_MEMBER)
                .build();
    }

    private Article setUpArticle(Member member) {
        return Article.builder()
                .member(member)
                .title("hello")
                .content("content1")
                .build();
    }

    private CreateCommentRequest createRequest(Long id, String content) {
        return new CreateCommentRequest(id, content);
    }
}