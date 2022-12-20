package me.snsservice.article.repository;

import me.snsservice.article.domain.Article;
import me.snsservice.common.NoOffsetPageRequest;
import me.snsservice.config.JpaAuditingConfig;
import me.snsservice.config.QueryDslConfig;
import me.snsservice.member.domain.Member;
import me.snsservice.member.domain.Role;
import me.snsservice.member.repository.MemberRepository;
import me.snsservice.tag.domain.Tag;
import me.snsservice.tag.domain.Tags;
import me.snsservice.tag.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import({JpaAuditingConfig.class, QueryDslConfig.class})
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void init() {
        Member member = new Member("test1234@test.com", "common", "password123!", Role.ROLE_MEMBER);
        memberRepository.save(member);
        List<Tag> tags1 = List.of(
                new Tag("Java"),
                new Tag("Spring"),
                new Tag("Kotlin"),
                new Tag("Typescript"),
                new Tag("JavaScript")
        );
        tagRepository.saveAll(tags1);
        List<Tag> tags2 = List.of(
                new Tag("NodeJs"),
                new Tag("NestJs"),
                new Tag("React"),
                new Tag("NextJs"),
                new Tag("Redux")
        );
        tagRepository.saveAll(tags2);

        Article article1 = new Article("자바하실?", "컨텐츠입니다.", member);
        article1.addTags(new Tags(tags1));

        Article article2 = new Article("타이틀입니다", "컨텐츠입니다.", member);
        article2.addTags(new Tags(tags2));
        Article article3 = new Article("타이틀입니다1", "컨텐츠입니다1.", member);
        article3.addTags(new Tags(tags1));
        Article article4 = new Article("타이틀입니다1", "컨텐츠입니다1.", member);
        article4.addTags(new Tags(tags1));
        Article article5 = new Article("타이틀입니다1", "컨텐츠입니다1.", member);
        article5.addTags(new Tags(tags1));
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        articleRepository.save(article4);
        articleRepository.save(article5);
    }

    @Test
    @DisplayName("태그로 검색시 ArticleIds 값을 리스트 형태로 반환합니다.")
    void findArticleIdsTest() {
        List<String> list = List.of("Java", "Kotlin", "Spring");
        NoOffsetPageRequest noOffsetPageRequest = NoOffsetPageRequest.of(null, 10);
        List<Long> articleIds = articleRepository.findAllArticleIdsByTagNames(list, noOffsetPageRequest);
        List<Article> articles = articleRepository.findAllArticlesByIdIn(articleIds);

        assertThat(articleIds.size()).isEqualTo(4);
        assertThat(articles.size()).isEqualTo(4);
    }
}