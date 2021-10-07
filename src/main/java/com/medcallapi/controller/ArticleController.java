package com.medcallapi.controller;

import com.medcallapi.entity.Article;
import com.medcallapi.request.ArticleRequest;
import com.medcallapi.response.ArticleResponse;
import com.medcallapi.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/articles")
@CrossOrigin
public class ArticleController {

    private ArticleService articleService;

    @GetMapping(path = "/")
    public List<ArticleResponse> index() {
        return articleService.index();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<Article>> show(@PathVariable Long id) {
        return articleService.show(id);
    }

    @PostMapping(path = "/new")
    public ResponseEntity<String> store(@RequestBody ArticleRequest articleRequest) {
        return articleService.store(articleRequest);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> update(
            @RequestBody ArticleRequest articleRequest,
            @PathVariable Long id
    ) {
        return articleService.update(articleRequest, id);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> destroy(@PathVariable Long id) {
        return articleService.destroy(id);
    }

}
