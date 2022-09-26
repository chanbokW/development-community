package me.snsservice.article.service;

import me.snsservice.article.dto.ArticleRequest;
import me.snsservice.article.dto.ArticleResponse;
import me.snsservice.article.repository.ArticleRepository;
import me.snsservice.member.domain.Member;
import me.snsservice.member.domain.Role;
import me.snsservice.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
        member = memberRepository.save(new Member("korea123@gmail.com", "hello", "pw0011", Role.MEMBER));
    }

    @Test
    @DisplayName("게시물을 등록한다.")
    void createArticle() {
        ArticleRequest articleRequest = new ArticleRequest("안녕하세요", "안녕하세요.", List.of("인사", "가입"));

        Long articleId = articleService.createArticle(articleRequest, member.getId());

        assertThat(articleId).isEqualTo(1L);
    }

    @Test
    @DisplayName("존재하지 않은 회원이 게시물을 작성시 예외가 발생한다.")
    void createArticleNotMemberExceptionTest() {
        ArticleRequest articleRequest = new ArticleRequest("안녕하세요", "안녕하세요.", List.of("인사", "가입"));

        assertThatThrownBy(() -> articleService.createArticle(articleRequest, 0L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않은 회원입니다.");
    }

    @Test
    @DisplayName("게시물 하나를 조회한다")
    void findArticleOne() {
        ArticleRequest articleRequest = new ArticleRequest("안녕하세요", "안녕하세요.", List.of("인사", "가입"));

        Long articleId = articleService.createArticle(articleRequest, member.getId());

        ArticleResponse findArticle = articleService.findById(articleId);

        assertThat(findArticle.getId()).isEqualTo(articleId);
        assertThat(findArticle.getNickname()).isEqualTo(member.getNickname());
        assertThat(findArticle.getTitle()).isEqualTo(articleRequest.getTitle());
    }
}