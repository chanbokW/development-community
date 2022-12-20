package me.snsservice.article.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ArticleOptionType {

    ALL,
    TITLE,
    CONTENT,
    TITLE_CONTENT;

}
