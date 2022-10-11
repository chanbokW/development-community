package me.snsservice.article.repository;

import lombok.Getter;

import java.util.List;

@Getter
public class ArticleQueryDto {

    private Long id;
    private String title;
    private String content;
    private Long view;

    //member
    private String nickname;

    // like tag
    private List<String> tags;
    private int likeCount;

    public ArticleQueryDto(Long id, String title, String content, Long view, String nickname) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.view = view;
        this.nickname = nickname;
    }

    public ArticleQueryDto(Long id, String title, String content, Long view, String nickname,int likeCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.view = view;
        this.nickname = nickname;
        this.likeCount = likeCount;
    }

    public ArticleQueryDto(Long id, String title, String content, Long view, String nickname, List<String> tags, int likeCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.view = view;
        this.nickname = nickname;
        this.tags = tags;
        this.likeCount = likeCount;
    }
}
