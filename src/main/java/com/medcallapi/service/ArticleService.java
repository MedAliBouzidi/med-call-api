package com.medcallapi.service;

import com.medcallapi.entity.Article;
import com.medcallapi.entity.UserEntity;
import com.medcallapi.repository.ArticleRepository;
import com.medcallapi.repository.UserRepository;
import com.medcallapi.request.ArticleRequest;
import com.medcallapi.response.ArticleResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ArticleService {
    private ArticleRepository articleRepository;
    private UserRepository userRepository;

    public List<Article> index() { return articleRepository.findAll(); }

    public ResponseEntity<Optional<Article>> show(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        return article.isPresent() ? ResponseEntity.status(HttpStatus.OK).body(article) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<String> store(ArticleRequest articleRequest) {
        UserEntity user = userRepository.findByUsername(articleRequest.getUsername());
        Article article = new Article(
                articleRequest.getTitle(),
                articleRequest.getContent(),
                articleRequest.getSpeciality()
        );
        article.setUser(user);
        articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).body("Article added successfully!");
    }

    public ResponseEntity<String> update(ArticleRequest articleRequest, Long id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            article.get().setTitle(articleRequest.getTitle());
            article.get().setContent(articleRequest.getContent());
            article.get().setSpeciality(articleRequest.getSpeciality());
            article.get().setUpdatedAt(new Date());
            articleRepository.save(article.get());
            return ResponseEntity.status(HttpStatus.OK).body("Updated successfully!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<String> destroy(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            articleRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
