package me.snsservice.tag.domain;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

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
            throw new IllegalArgumentException();
        }
    }
}
