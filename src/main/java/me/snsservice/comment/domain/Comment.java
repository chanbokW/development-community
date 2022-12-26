package me.snsservice.comment.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.snsservice.article.domain.Article;
import me.snsservice.common.domain.BaseTimeEntity;
import me.snsservice.member.domain.Member;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    private String content;

    private Boolean activated = true;


    public Comment(Member member, Article article, String content) {
        this.member = member;
        this.article = article;
        this.content = content;
    }

    public void updateCommnet(String contents) {
        this.content = contents;
    }
}
