package me.snsservice.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import me.snsservice.article.domain.Article;

import java.time.LocalDateTime;

@Getter
public class ArticleListResponse {

    private Long articleId;
    private String title;
    private String content;

    private Long viewCount;
    private Long memberId;
    private String nickname;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime createAt;

    @Builder
    public ArticleListResponse(Long articleId, String title, String content, Long viewCount, Long memberId, String nickname, LocalDateTime createAt) {
        this.articleId = articleId;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.memberId = memberId;
        this.nickname = nickname;
        this.createAt = createAt;
    }

    public static ArticleListResponse of(Article article) {
        return ArticleListResponse.builder()
                .articleId(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .viewCount(article.getViewCount())
                .memberId(article.getMember().getId())
                .nickname(article.getMember().getNickname())
                .createAt(article.getCreateDate())
                .build();
    }
}
