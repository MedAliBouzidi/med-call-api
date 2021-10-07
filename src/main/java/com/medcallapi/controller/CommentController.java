package com.medcallapi.controller;

import com.medcallapi.request.CommentRequest;
import com.medcallapi.response.CommentResponse;
import com.medcallapi.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/articles")
@CrossOrigin
public class CommentController {

    private CommentService commentService;

    @GetMapping(path = "/{id}/comments")
    public List<CommentResponse> index(@PathVariable Long id) {
        return commentService.getCommentsForArticle(id);
    }

    @PostMapping(path = "/{id}/comments/new")
    public ResponseEntity<CommentResponse> store(
            @PathVariable Long id,
            @RequestBody CommentRequest commentRequest
    ) { return commentService.store(id, commentRequest); }

    @PutMapping(path = "/{article_id}/comments/{comment_id}")
    public ResponseEntity<CommentResponse> update(
            @PathVariable Long article_id,
            @PathVariable Long comment_id,
            @RequestBody CommentRequest commentRequest
    ) { return commentService.update(article_id, comment_id, commentRequest); }

    @DeleteMapping(path = "/{article_id}/comments/{comment_id}")
    public ResponseEntity<String> destroy(
            @PathVariable Long article_id,
            @PathVariable Long comment_id
    ) { return commentService.destroy(article_id, comment_id); }

}
