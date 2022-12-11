package me.snsservice.article.service;

import me.snsservice.article.domain.Article;
import me.snsservice.article.dto.CreateArticleRequest;
import me.snsservice.article.dto.ArticleResponse;
import me.snsservice.article.dto.UpdateArticleRequest;
import me.snsservice.article.repository.ArticleRepository;
import me.snsservice.common.exception.ErrorCode;
import me.snsservice.common.exception.BusinessException;
import me.snsservice.member.domain.Member;
import me.snsservice.member.domain.Role;
import me.snsservice.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ArticleRepository articleRepository;
    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("korea123@gmail.com", "hello", "pw00111!1", Role.ROLE_MEMBER));
    }

    @Test
    @DisplayName("게시물을 수정을 할 때 권한이 없으면 예외가 발생한다.")
    void modifyArticleMemberExceptionTest() {
        CreateArticleRequest createArticleRequest = getCreateArticleRequest();
        Long articleId = articleService.createArticle(createArticleRequest, member);

        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest("하이요", "바이요");

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> articleService.updateArticle(updateArticleRequest, articleId, 100000001L)
        );

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.UNAUTHORIZED_ARTICLE_MEMBER);
    }


    @Test
    @DisplayName("게시물을 수정을 할 때 게시물이 존재하지 않을 때 예외가 발생한다.")
    void modifyArticleIdExceptionTest() {
        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest("하이요", "바이요");
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> articleService.updateArticle(updateArticleRequest, 2000000L, member.getId())
        );
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_ARTICLE);
    }

    @Test
    @DisplayName("게시물을 수정을 할 때 권한이 없으면 예외가 발생한다.")
    void modifyArticleExceptionTest() {
        CreateArticleRequest createArticleRequest = getCreateArticleRequest();
        Long articleId = articleService.createArticle(createArticleRequest, member);

        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest("하이요", "바이요");

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> articleService.updateArticle(updateArticleRequest, articleId, 100000001L)
        );

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.UNAUTHORIZED_ARTICLE_MEMBER);
    }

    @Test
    @DisplayName("게시물을 등록한다.")
    void createArticle() {
        CreateArticleRequest createArticleRequest = getCreateArticleRequest();

        Long articleId = articleService.createArticle(createArticleRequest, member);
        Article article = articleRepository.findById(articleId).get();

        assertThat(articleId).isNotNull();
        assertThat(articleId).isEqualTo(article.getId());
    }

    @Test
    @DisplayName("게시물 하나를 조회한다")
    void findArticleOne() {
        CreateArticleRequest createArticleRequest = getCreateArticleRequest();

        Long articleId = articleService.createArticle(createArticleRequest, member);

        ArticleResponse findArticle = articleService.findById(articleId);

        assertThat(findArticle.getId()).isEqualTo(articleId);
        assertThat(findArticle.getNickname()).isEqualTo(member.getNickname());
        assertThat(findArticle.getTitle()).isEqualTo(createArticleRequest.getTitle());
        assertThat(findArticle.getView()).isEqualTo(1L);
    }

    @Test
    @DisplayName("게시물을 수정한다.")
    void modifyArticleTest() {
        CreateArticleRequest createArticleRequest = getCreateArticleRequest();
        Long articleId = articleService.createArticle(createArticleRequest, member);

        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest("하이요", "바이요");

        articleService.updateArticle(updateArticleRequest, articleId, member.getId());
        ArticleResponse findArticle = articleService.findById(articleId);

        assertThat(findArticle.getId()).isEqualTo(articleId);
        assertThat(findArticle.getTitle()).isEqualTo(updateArticleRequest.getTitle());
        assertThat(findArticle.getContent()).isEqualTo(updateArticleRequest.getContent());
    }

    @Test
    @DisplayName("게시물을 삭제한다.")
    void deleteArticleTest() {
        CreateArticleRequest createArticleRequest = getCreateArticleRequest();
        Long articleId = articleService.createArticle(createArticleRequest, member);

        articleService.deleteArticle(articleId, member.getId());
        Article article = articleRepository.findById(articleId).get();

        assertThat(article.getActivated()).isFalse();
    }

    @Test
    @DisplayName("게시물을 삭제를 할 때 게시물이 존재하지 않을 때 예외가 발생한다.")
    void deleteArticleIdExceptionTest() {
        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest("하이요", "바이요");
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> articleService.updateArticle(updateArticleRequest, 2000000L, member.getId())
        );
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.NOT_FOUND_ARTICLE);
    }

    @Test
    @DisplayName("게시물을 삭제를 할 때 권한이 없으면 예외가 발생한다.")
    void deleteArticleExceptionTest() {
        CreateArticleRequest createArticleRequest = getCreateArticleRequest();
        Long articleId = articleService.createArticle(createArticleRequest, member);
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> articleService.deleteArticle(articleId, 100000001L)
        );

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.UNAUTHORIZED_ARTICLE_MEMBER);
    }
    private CreateArticleRequest getCreateArticleRequest() {
        return new CreateArticleRequest("안녕하세요", "안녕하세요.", List.of("인사", "가입"));
    }

}