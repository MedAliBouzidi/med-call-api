package com.medcallapi.service;

import com.medcallapi.entity.Article;
import com.medcallapi.repository.ArticleRepository;
import com.medcallapi.response.ArticleResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ArticleService {
    private ArticleRepository articleRepository;

    public List<ArticleResponse> index() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleResponse> articlesResponse = new ArrayList<>();
        for (Article article : articles) {
            articlesResponse.add(new ArticleResponse(article));
        }
        return articlesResponse;
    }

    public ResponseEntity<Optional<Article>> show(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        return article.isPresent() ? ResponseEntity.status(HttpStatus.FOUND).body(article) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
