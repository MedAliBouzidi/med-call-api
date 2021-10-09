package com.medcallapi.service;

import com.medcallapi.entity.Article;
import com.medcallapi.entity.UserEntity;
import com.medcallapi.repository.ArticleRepository;
import com.medcallapi.repository.UserRepository;
import com.medcallapi.request.ArticleRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ArticleService {
    private ArticleRepository articleRepository;
    private UserRepository userRepository;

    public List<Article> index() { return articleRepository.findAllByOrderByUpdatedAtDesc(); }

    public ResponseEntity<Article> show(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        return article
                .map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<Void> store(ArticleRequest articleRequest) {
        UserEntity user = userRepository.findByUsername(articleRequest.getUsername());
        Article article = new Article(
                articleRequest.getTitle(),
                articleRequest.getContent(),
                articleRequest.getSpeciality()
        );
        article.setUser(user);
        if (user.getRole().equals("ADMIN")) article.setValidated(true);
        articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Void> update(ArticleRequest articleRequest, Long id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            article.get().setTitle(articleRequest.getTitle());
            article.get().setContent(articleRequest.getContent());
            article.get().setSpeciality(articleRequest.getSpeciality());
            article.get().setUpdatedAt(new Date());
            article.get().setValidated(false);
            articleRepository.save(article.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<Void> destroy(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            articleRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
