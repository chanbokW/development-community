package me.snsservice.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import me.snsservice.article.domain.Article;

import java.time.LocalDateTime;

@Getter
public class ArticleListResponse {

    private Long id;
    private String title;
    private String content;
    private String nickname;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:SS")
//    private LocalDateTime createAt;

    private ArticleListResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.nickname = article.getMember().getNickname();

    }

    public ArticleListResponse(Article article, String nickname) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.nickname = nickname;
    }

    public static ArticleListResponse of(Article article) {
        return new ArticleListResponse(article);
    }
}
