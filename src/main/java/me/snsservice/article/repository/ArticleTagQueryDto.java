package me.snsservice.article.repository;

import lombok.Getter;

@Getter
public class ArticleTagQueryDto {

    private Long articleId;
    private String tagName;

    public ArticleTagQueryDto(Long articleId, String tagName) {
        this.articleId = articleId;
        this.tagName = tagName;
    }
}
