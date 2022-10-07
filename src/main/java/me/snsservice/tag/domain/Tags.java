package me.snsservice.tag.domain;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import me.snsservice.common.error.ErrorCode;
import me.snsservice.common.error.exception.BusinessException;

@Getter
public class Tags {

    private List<Tag> tags;

    public Tags(List<Tag> tags) {
        validateDuplicateTag(tags);
        this.tags = tags;
    }

    public static Tags from(List<String> tags) {
        return new Tags(tags.stream()
                .map(Tag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Tag 이름 중복 검사
     */
    private void validateDuplicateTag(List<Tag> tags) {
        long count = tags.stream()
                .map(Tag::getName)
                .distinct()
                .count();

        if (count != tags.size()) {
            throw new BusinessException(ErrorCode.DUPLICATE_TAG);
        }
    }

    public List<String> getTagNames() {
        return tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
    }
}
