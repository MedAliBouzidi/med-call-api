package com.medcallapi.controller;

import com.medcallapi.entity.Article;
import com.medcallapi.response.ArticleResponse;
import com.medcallapi.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ArticleController {

    private ArticleService articleService;

    @GetMapping(path = "/articles")
    public List<ArticleResponse> index() {
        return articleService.index();
    }

    @GetMapping(path = "/articles/{id}")
    public ResponseEntity<Optional<Article>> show(@PathVariable Long id) {
        return articleService.show(id);
    }

    // Add New Article

    // Update Article

    // Delete Article

}
