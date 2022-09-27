package me.snsservice.article.dto;

import lombok.Builder;
import lombok.Getter;
import me.snsservice.article.domain.Article;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ArticleResponse {

    private Long id;
    private String title;
    private String content;
    private Long view;

    //Todo 따로 response객체를 만들거나 해야함
    private String nickname;
    //Todo 태그와 좋아요 기능추가
    private int likeCount;
    private List<String> tags;

    @Builder
    private ArticleResponse(Long id, String title, String content, Long view, String nickname, int likeCount, List<String> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.view = view;
        this.nickname = nickname;
        this.likeCount = likeCount;
        this.tags = tags;
    }

    public static ArticleResponse of(Article article) {
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .nickname(article.getMember().getNickname())
                .view(article.getViewCount())
                .likeCount(article.getLikes().size())
                .tags(article.getArticleTags().stream()// Todo 도메인에 설계하는게 좋을 거 같기도
                        .map(articleTag -> articleTag.getTag().getName())
                        .collect(Collectors.toList())
                )
                .build();
    }
}
