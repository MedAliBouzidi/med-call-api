package com.medcallapi.controller;

import com.medcallapi.entity.Article;
import com.medcallapi.request.ArticleRequest;
import com.medcallapi.response.ArticleResponse;
import com.medcallapi.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping(path = "/articles/new")
    public ResponseEntity<String> store(@RequestBody ArticleRequest articleRequest) {
        return articleService.store(articleRequest);
    }

    @PutMapping(path = "/articles/{id}")
    public ResponseEntity<String> update(
            @RequestBody ArticleRequest articleRequest,
            @PathVariable Long id
    ) {
        return articleService.update(articleRequest, id);
    }

    @DeleteMapping(path = "/articles/{id}")
    public ResponseEntity<String> destroy(@PathVariable Long id) {
        return articleService.destroy(id);
    }

}
