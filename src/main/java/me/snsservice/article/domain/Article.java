package me.snsservice.article.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.snsservice.member.domain.Member;
import me.snsservice.tag.domain.Tags;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    // Todo 추후에 칼럼이 추가 될 수 있습니다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Boolean activated = true;

    private Long viewCount;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticleTag> articleTags = new ArrayList<>();

    @Builder
    public Article(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.viewCount = 0L;
    }

    public void addViewCount() {
        this.viewCount += 1;
    }

    public void updateArticle(String title, String content) {
        if (title != null) {
            this.title = title;
        }
        if (content != null) {
            this.content = content;
        }
    }

    public void deleteArticle() {
        this.activated = false;
    }

    public void addTags(Tags tags) {
        tags.getTags()
                .forEach(tag ->
                        articleTags.add(new ArticleTag(this, tag)
                        )
                );
    }
}
