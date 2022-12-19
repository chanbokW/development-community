package me.snsservice.article.controller;


public class ArticleSearchOption {

    private String optionType;
    private String keyword;

    public ArticleSearchOption(String optionType, String keyword) {
        this.optionType = optionType;
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public ArticleOptionType getOptionType() {
        return (optionType == null) ? null : ArticleOptionType.valueOf(optionType.toUpperCase());
    }
}
