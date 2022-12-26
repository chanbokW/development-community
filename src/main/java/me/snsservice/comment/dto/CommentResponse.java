package me.snsservice.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.snsservice.comment.domain.Comment;
import me.snsservice.member.dto.MemberResponse;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private Long commentId;
    private String content;

    private MemberResponse writer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:SS")
    private LocalDateTime createAt;

    @Builder
    public CommentResponse(Long commentId, String content, MemberResponse writer, LocalDateTime createAt) {
        this.commentId = commentId;
        this.content = content;
        this.writer = writer;
        this.createAt = createAt;
    }

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .writer(MemberResponse.of(comment.getMember()))
                .createAt(comment.getCreateDate())
                .build();

    }
}
