package com.medcallapi.response;

import com.medcallapi.entity.Article;
import lombok.Getter;

@Getter
public class ArticleResponse {
    private final Article article;

    public ArticleResponse(Article article) {
        this.article = article;
        this.article.getUser().setPassword("");
    }
}
