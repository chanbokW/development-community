package me.snsservice.tag.domain;

import me.snsservice.common.error.ErrorCode;
import me.snsservice.common.error.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}