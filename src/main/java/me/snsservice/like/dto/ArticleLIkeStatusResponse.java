package me.snsservice.like.dto;

import lombok.Builder;
import lombok.Getter;
import me.snsservice.article.domain.Article;

@Getter
public class ArticleLIkeStatusResponse {
    private Boolean isLike;
    private Article article;

    @Builder
    public ArticleLIkeStatusResponse(Boolean isLike, Article article) {
        this.isLike = isLike;
        this.article = article;
    }
}
