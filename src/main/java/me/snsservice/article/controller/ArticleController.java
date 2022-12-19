package me.snsservice.article.controller;

import lombok.RequiredArgsConstructor;
import me.snsservice.article.dto.ArticleListResponse;
import me.snsservice.article.dto.CreateArticleRequest;
import me.snsservice.article.dto.ArticleResponse;
import me.snsservice.article.dto.UpdateArticleRequest;
import me.snsservice.article.service.ArticleService;
import me.snsservice.auth.controller.Login;
import me.snsservice.auth.controller.LoginMember;
import me.snsservice.common.response.CommonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public CommonResponse<Long> createArticle(@RequestBody CreateArticleRequest createArticleRequest,
                                              @Login LoginMember loginMember) {
        Long articleId = articleService.createArticle(createArticleRequest, loginMember.getId());
        return CommonResponse.res(HttpStatus.CREATED, "게시물 생성", articleId);
    }

    @GetMapping("/{articleId}")
    public CommonResponse<ArticleResponse> findOneArticle(@PathVariable Long articleId) {
        ArticleResponse articleResponse = articleService.findById(articleId);
        return CommonResponse.res(HttpStatus.OK, "게시물 단건 조회", articleResponse);
    }

    @GetMapping
    public CommonResponse<List<ArticleListResponse>> findAllArticle(
            ArticleSearchOption articleSearchOption,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<ArticleListResponse> articles = articleService.findAllWithArticle(articleSearchOption, page - 1, size);
        return CommonResponse.res(HttpStatus.OK, "게시물 전체 및 검색 조회", articles);
    }

    @GetMapping("/search/tags")
    public CommonResponse<?> findAllSearchArticles(ArticleSearchOption searchArticle) {
        return null;
    }

    @PutMapping("/{articleId}")
    public CommonResponse<Void> updateArticle(@PathVariable Long articleId,
                                              @RequestBody UpdateArticleRequest updateArticleRequest, @Login LoginMember loginMember) {
        articleService.updateArticle(updateArticleRequest, articleId, loginMember.getId());
        return CommonResponse.res(HttpStatus.OK, "게시물 수정");
    }

    @DeleteMapping("/{articleId}")
    public CommonResponse<Void> deleteArticle(@PathVariable Long articleId, @Login LoginMember loginMember) {
        articleService.deleteArticle(articleId, loginMember.getId());
        return CommonResponse.res(HttpStatus.OK, "게시물 삭제");
    }
}