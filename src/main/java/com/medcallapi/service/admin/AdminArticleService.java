package com.medcallapi.service.admin;

import com.medcallapi.entity.Article;
import com.medcallapi.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminArticleService {
    private ArticleRepository articleRepository;

    public List<Article> index() { return articleRepository.findAll(); }

    public ResponseEntity<Void> toggleValidated(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            article.get().setValidated(!article.get().isValidated());
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
