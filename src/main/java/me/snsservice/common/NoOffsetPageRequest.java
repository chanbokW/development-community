package me.snsservice.common;

import lombok.Getter;

import java.util.Optional;

@Getter
public class NoOffsetPageRequest {
    private long current;
    private int size;

    public NoOffsetPageRequest(Long current, int size) {
        this.current = Optional.ofNullable(current).orElse(Long.MAX_VALUE);
        this.size = size;
    }

    public static NoOffsetPageRequest of(Long current, int size) {
        return new NoOffsetPageRequest(current, size);
    }

}
