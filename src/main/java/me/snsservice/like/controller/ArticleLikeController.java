package me.snsservice.like.controller;

import lombok.RequiredArgsConstructor;
import me.snsservice.common.jwt.anotation.LoginMember;
import me.snsservice.common.response.CommonResponse;
import me.snsservice.like.dto.ArticleLIkeStatusResponse;
import me.snsservice.like.service.ArticleLikeService;
import me.snsservice.member.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleLikeController {

    private final ArticleLikeService articleLikeService;

    @PostMapping("/{articleId}/like")
    public CommonResponse<Boolean> like(@PathVariable Long articleId, @LoginMember Member member) {
        ArticleLIkeStatusResponse articleAndLike = articleLikeService.getArticleAndLike(articleId, member);
        if (articleAndLike.getIsLike()) {
            articleLikeService.unlike(articleAndLike.getArticle(), member);
            return CommonResponse.res(HttpStatus.OK, "좋아요 취소", false);
        }
        articleLikeService.like(articleAndLike.getArticle(), member);
        return CommonResponse.res(HttpStatus.OK, "좋아요", true);
    }
}
