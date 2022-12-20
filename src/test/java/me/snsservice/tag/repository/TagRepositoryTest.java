package me.snsservice.tag.repository;

import me.snsservice.config.JpaAuditingConfig;
import me.snsservice.config.QueryDslConfig;
import me.snsservice.tag.domain.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({JpaAuditingConfig.class, QueryDslConfig.class})
public class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void init() {
        List<Tag> tags = List.of(
                new Tag("하이"),
                new Tag("스프링"),
                new Tag("NodeJs"),
                new Tag("Java"),
                new Tag("kotlin")
        );
        tagRepository.saveAll(tags);
    }

    @Test
    @DisplayName("저장된 태그 IN 절로 확인 테스트")
    void findByNameIn() {
        List<String> createTags = List.of("스프링", "Java", "kotlin");

        List<Tag> findTags = tagRepository.findByNameIn(createTags);

        assertThat(findTags.size()).isEqualTo(3);
        assertThat(findTags).extracting("name")
                .contains("스프링", "Java", "kotlin")
                .doesNotContain("NodeJs", "하이");
    }
}