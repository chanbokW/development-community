package me.snsservice.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import me.snsservice.article.domain.Article;
import me.snsservice.member.dto.MemberResponse;

import java.time.LocalDateTime;

@Getter
public class ArticleListResponse {

    private Long articleId;
    private String title;
    private String content;

    private Long viewCount;
    private MemberResponse memberResponse;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime createAt;

    @Builder
    public ArticleListResponse(Long articleId, String title, String content, Long viewCount, MemberResponse memberResponse, LocalDateTime createAt) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.memberResponse = memberResponse;
        this.createAt = createAt;
    }

    public static ArticleListResponse of(Article article) {
        return ArticleListResponse.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .viewCount(article.getViewCount())
                .memberResponse(MemberResponse.of(article.getMember()))
                .createAt(article.getCreateDate())
                .build();
    }
}
