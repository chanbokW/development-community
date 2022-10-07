package me.snsservice.article.service;

import me.snsservice.article.domain.Article;
import me.snsservice.article.dto.CreateArticleRequest;
import me.snsservice.article.dto.ArticleResponse;
import me.snsservice.article.repository.ArticleRepository;
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
        member = memberRepository.save(new Member("korea123@gmail.com", "hello", "pw0011", Role.ROLE_MEMBER));
    }

    @Test
    @DisplayName("게시물을 등록한다.")
    void createArticle() {
        CreateArticleRequest createArticleRequest = new CreateArticleRequest("안녕하세요", "안녕하세요.", List.of("인사", "가입"));

        Long articleId = articleService.createArticle(createArticleRequest, member);
        Article article = articleRepository.findById(articleId).get();

        assertThat(articleId).isNotNull();
        assertThat(articleId).isEqualTo(article.getId());
    }

    @Test
    @DisplayName("게시물 하나를 조회한다")
    void findArticleOne() {
        CreateArticleRequest createArticleRequest = new CreateArticleRequest("안녕하세요", "안녕하세요.", List.of("인사", "가입"));

        Long articleId = articleService.createArticle(createArticleRequest, member);

        ArticleResponse findArticle = articleService.findById(articleId);

        assertThat(findArticle.getId()).isEqualTo(articleId);
        assertThat(findArticle.getNickname()).isEqualTo(member.getNickname());
        assertThat(findArticle.getTitle()).isEqualTo(createArticleRequest.getTitle());
        assertThat(findArticle.getView()).isEqualTo(1L);
    }
}