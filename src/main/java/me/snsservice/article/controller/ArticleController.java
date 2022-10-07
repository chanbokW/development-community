package me.snsservice.article.controller;

import lombok.RequiredArgsConstructor;
import me.snsservice.article.dto.CreateArticleRequest;
import me.snsservice.article.dto.ArticleResponse;
import me.snsservice.article.dto.UpdateArticleRequest;
import me.snsservice.article.service.ArticleService;
import me.snsservice.common.jwt.anotation.LoginMember;
import me.snsservice.member.domain.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<Long> createArticle(@RequestBody CreateArticleRequest createArticleRequest,
                                              @LoginMember Member member) {
        return ResponseEntity.ok().body(articleService.createArticle(createArticleRequest, member));
    }

    @GetMapping("{articleId}")
    public ResponseEntity<ArticleResponse> findOneArticle(@PathVariable Long articleId) {
        return ResponseEntity.ok().body(articleService.findById(articleId));
    }

    // Todo 페이징
    @GetMapping
    public ResponseEntity<List<ArticleResponse>> findAllArticle() {
        return ResponseEntity.ok().body(articleService.findAll());
    }

    @PutMapping("{articleId}")
    public ResponseEntity<Void> updateArticle(@PathVariable Long articleId,
              @RequestBody UpdateArticleRequest updateArticleRequest, @LoginMember Member member) {
        articleService.updateArticle(updateArticleRequest, articleId, member.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long articleId, @LoginMember Member member) {
        articleService.deleteArticle(articleId, member.getId());
        return ResponseEntity.ok().build();
    }
}
