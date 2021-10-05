package com.medcallapi.controller;

import com.medcallapi.response.ArticleResponse;
import com.medcallapi.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ArticleController {

    private ArticleService articleService;

    @GetMapping(path = "/article")
    public List<ArticleResponse> index() {
        return articleService.index();
    }

}
