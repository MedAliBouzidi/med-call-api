package com.medcallapi.controller.admin;

import com.medcallapi.entity.Article;
import com.medcallapi.service.admin.AdminArticleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/articles")
@CrossOrigin
public class AdminArticleController {

    private AdminArticleService articleService;

    @GetMapping(path = "")
    public List<Article> index() {
        return articleService.index();
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Void> toggleValidated(@PathVariable Long id) {
        return articleService.toggleValidated(id);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> destroy(@PathVariable Long id) {
        return articleService.destroy(id);
    }


}
