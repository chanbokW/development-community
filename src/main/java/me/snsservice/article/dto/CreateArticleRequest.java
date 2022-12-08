package me.snsservice.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.snsservice.article.domain.Article;
import me.snsservice.member.domain.Member;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleRequest {
    private String title;
    private String content;
    private List<String> tags;

    public Article toEntity(Member member) {
        return Article.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }
}
