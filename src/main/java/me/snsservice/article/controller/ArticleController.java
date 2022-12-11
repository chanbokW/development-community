package me.snsservice.article.controller;

import lombok.RequiredArgsConstructor;
import me.snsservice.article.dto.ArticleListResponse;
import me.snsservice.article.dto.CreateArticleRequest;
import me.snsservice.article.dto.ArticleResponse;
import me.snsservice.article.dto.UpdateArticleRequest;
import me.snsservice.article.service.ArticleService;
import me.snsservice.token.annotation.LoginMember;
import me.snsservice.common.response.CommonResponse;
import me.snsservice.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public CommonResponse<Long> createArticle(@RequestBody CreateArticleRequest createArticleRequest,
                                              @LoginMember Member member) {
        return CommonResponse
                .res(HttpStatus.CREATED, "게시물 생성", articleService.createArticle(createArticleRequest, member));
    }

    @GetMapping("/{articleId}")
    public CommonResponse<ArticleResponse> findOneArticle(@PathVariable Long articleId) {
        return CommonResponse.res(HttpStatus.OK, "게시물 단건 조회", articleService.findById(articleId));
    }

    // Todo 페이징
    @GetMapping
    public CommonResponse<Page<ArticleListResponse>> findAllArticle(Pageable pageable) {
        return CommonResponse.res(HttpStatus.OK, "게시물 전체 조회", articleService.findAllWithArticle(pageable));
    }

    @PutMapping("/{articleId}")
    public CommonResponse<Void> updateArticle(@PathVariable Long articleId,
                                              @RequestBody UpdateArticleRequest updateArticleRequest, @LoginMember Member member) {
        articleService.updateArticle(updateArticleRequest, articleId, member.getId());
        return CommonResponse.res(HttpStatus.OK, "게시물 수정");
    }

    @DeleteMapping("/{articleId}")
    public CommonResponse<Void> deleteArticle(@PathVariable Long articleId, @LoginMember Member member) {
        articleService.deleteArticle(articleId, member.getId());
        return CommonResponse.res(HttpStatus.OK, "게시물 삭제");
    }
}