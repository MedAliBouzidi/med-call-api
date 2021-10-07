package com.medcallapi.response;

import com.medcallapi.entity.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleResponse {
    private Article article;

    public ArticleResponse(Article article) {
        this.article = article;
        this.article.getUser().setPassword("");
    }
}
