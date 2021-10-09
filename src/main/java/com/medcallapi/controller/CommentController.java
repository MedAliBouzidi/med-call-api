package com.medcallapi.controller;

import com.medcallapi.entity.Comment;
import com.medcallapi.request.CommentRequest;
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

    @GetMapping(path = "/{article_id}/comments")
    public ResponseEntity<List<Comment>> index(@PathVariable Long article_id) {
        return commentService.getCommentsForArticle(article_id);
    }

    @PostMapping(path = "/{article_id}/comments/new")
    public ResponseEntity<Comment> store(
            @PathVariable Long article_id,
            @RequestBody CommentRequest commentRequest
    ) { return commentService.store(article_id, commentRequest); }

    @PutMapping(path = "/{article_id}/comments/{comment_id}")
    public ResponseEntity<Comment> update(
            @PathVariable Long article_id,
            @PathVariable Long comment_id,
            @RequestBody CommentRequest commentRequest
    ) { return commentService.update(article_id, comment_id, commentRequest); }

    @DeleteMapping(path = "/{article_id}/comments/{comment_id}")
    public ResponseEntity<Void> destroy(
            @PathVariable Long article_id,
            @PathVariable Long comment_id
    ) { return commentService.destroy(article_id, comment_id); }

}
