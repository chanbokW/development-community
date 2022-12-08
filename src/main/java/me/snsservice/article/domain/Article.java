package me.snsservice.article.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.snsservice.common.domain.BaseTimeEntity;
import me.snsservice.like.domain.ArticleLike;
import me.snsservice.member.domain.Member;
import me.snsservice.tag.domain.Tags;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends BaseTimeEntity {

    // Todo 추후에 칼럼이 추가 될 수 있습니다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Title title;

    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Boolean activated = true;

    private Long viewCount;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticleTag> articleTags = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    private List<ArticleLike> likes = new ArrayList<>();

    @Builder
    public Article(String title, String content, Member member) {
        this.title = new Title(title);
        this.content = new Content(content);
        this.member = member;
        this.viewCount = 0L;
    }

    public void addViewCount() {
        this.viewCount += 1;
    }

    public void updateArticle(String title, String content) {
        if (title != null) {
            this.title = new Title(title);
        }
        if (content != null) {
            this.content = new Content(content);
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

    public List<String> getTagNames() {
        return getArticleTags().stream()
                .map(articleTag ->  articleTag.getTag().getName())
                .collect(Collectors.toList());
    }

    public String getTitle() {
        return title.value();
    }

    public String getContent() {
        return content.value();
    }
}
