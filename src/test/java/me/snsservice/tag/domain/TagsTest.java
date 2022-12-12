package me.snsservice.tag.domain;

import me.snsservice.common.exception.ErrorCode;
import me.snsservice.common.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class TagsTest {

    @Test
    @DisplayName("태그를 성공 테스트")
    void createTagsTest() {
        List<String> taglist = List.of(
                "Hello", "Spring", "Good"
        );

        Tags tags = Tags.from(taglist);

        List<Tag> tags1 = tags.getTags();
        assertThat(tags1.get(0).getName()).isEqualTo(taglist.get(0));
        assertThat(tags1.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("중복 테그를 등록시 예외가 발생한다.")
    void validateDuplicateTagTest() {
        List<String> taglist = List.of(
                "Hello", "Hello", "Good"
        );

        BusinessException exception = assertThrows(BusinessException.class, () -> Tags.from(taglist));
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.DUPLICATE_TAG);
    }

    @Test
    @DisplayName("테그를 10개를 초과시 예외가 발생한다.")
    void validateMaxTagSizeTest() {
        List<String> tags = List.of(
                "Hello", "Java", "Good", "NodeJs", "NestJs", "Spring", "Jpa", "hibernate", "docker", "aws", "redis"
        );

        BusinessException exception = assertThrows(BusinessException.class, () -> Tags.from(tags));
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INVALID_TAGS_SIZE);
    }

    @Test
    @DisplayName("태그를 1개 미만으로 작성시 예외가 발생한다.")
    void validateMinTagSizeTest() {
        List<String> tags = new ArrayList<>();

        BusinessException exception = assertThrows(BusinessException.class, () -> Tags.from(tags));
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INVALID_TAGS_SIZE);
    }
}