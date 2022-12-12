package me.snsservice.tag.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import me.snsservice.common.exception.ErrorCode;
import me.snsservice.common.exception.BusinessException;


@Getter
public class Tags {

    private static final int TAGS_MIN_SIZE = 1;
    private static final int TAGS_MAX_SIZE = 10;
    private List<Tag> tags;

    public Tags(List<Tag> tags) {
        validateDuplicateTag(tags);
        validateTagsSize(tags);
        this.tags = tags;
    }

    private void validateTagsSize(List<Tag> tags) {
        int size = tags.size();
        if (size < TAGS_MIN_SIZE || size > TAGS_MAX_SIZE) {
            throw new BusinessException(ErrorCode.INVALID_TAGS_SIZE);
        }
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

    public Tags addAllTags(Tags newTag) {
        ArrayList<Tag> tags = new ArrayList<>(this.tags);
        for (Tag tag : newTag.getTags()) {
            tags.add(tag);
        }
        return new Tags(tags);
    }

    public List<String> getTagNames() {
        return tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
    }

    public Tags removeAllByName(Tags findTags) {
        return tags.stream()
                .filter(it -> findTags.getTags().stream()
                        .noneMatch(it::isSameName))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Tags::new));
    }
}
