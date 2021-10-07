package com.medcallapi.service;

import com.medcallapi.entity.Article;
import com.medcallapi.entity.Comment;
import com.medcallapi.entity.UserEntity;
import com.medcallapi.repository.ArticleRepository;
import com.medcallapi.repository.CommentRepository;
import com.medcallapi.repository.UserRepository;
import com.medcallapi.request.CommentRequest;
import com.medcallapi.response.CommentResponse;
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
public class CommentService {

    private CommentRepository commentRepository;
    private ArticleRepository articleRepository;
    private UserRepository userRepository;

    public List<CommentResponse> getCommentsForArticle(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        List<CommentResponse> commentResponses = new ArrayList<>();
        if (article.isPresent()) {
            List<Comment> comments = commentRepository.findByArticle(article.get());
            for (Comment comment : comments) {
                commentResponses.add(new CommentResponse(comment));
            }
        }
        return commentResponses;
    }

    public ResponseEntity<CommentResponse> store(Long id, CommentRequest commentRequest) {
        Comment comment = new Comment(commentRequest.getContent());
        Optional<Article> article = articleRepository.findById(id);
        UserEntity user = userRepository.findByUsername(commentRequest.getUsername());
        if (article.isPresent()) {
            comment.setArticle(article.get());
            comment.setUser(user);
            commentRepository.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CommentResponse(comment));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<CommentResponse> update(Long article_id, Long comment_id, CommentRequest commentRequest) {
        Optional<Article> article = articleRepository.findById(article_id);
        if (article.isPresent()) {
            Optional<Comment> comment = commentRepository.findById(comment_id);
            UserEntity user = userRepository.findByUsername(commentRequest.getUsername());
            if (comment.isPresent() && user == comment.get().getUser()) {
                comment.get().setContent(commentRequest.getContent());
                comment.get().setUpdatedAt(new Date());
                commentRepository.save(comment.get());
                return ResponseEntity.status(HttpStatus.OK).body(new CommentResponse(comment.get()));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<String> destroy(Long article_id, Long comment_id) {
        Optional<Article> article = articleRepository.findById(article_id);
        if (article.isPresent()) {
            Optional<Comment> comment = commentRepository.findById(comment_id);
            if (comment.isPresent()) {
                commentRepository.delete(comment.get());
                return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully!");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment Not Found!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article Not Found!");
    }
}
