package com.medcallapi.service;

import com.medcallapi.entity.Article;
import com.medcallapi.entity.Comment;
import com.medcallapi.entity.UserEntity;
import com.medcallapi.repository.ArticleRepository;
import com.medcallapi.repository.CommentRepository;
import com.medcallapi.repository.UserRepository;
import com.medcallapi.request.CommentRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentService {

    private CommentRepository commentRepository;
    private ArticleRepository articleRepository;
    private UserRepository userRepository;

    public ResponseEntity<List<Comment>> getCommentsForArticle(Long article_id) {
        Optional<Article> article = articleRepository.findById(article_id);
        if (article.isPresent()) {
            List<Comment> comments = commentRepository.findByArticleOrderByUpdatedAtDesc(article.get());
            return ResponseEntity.status(HttpStatus.OK).body(comments);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<Comment> store(Long article_id, CommentRequest commentRequest) {
        Comment comment = new Comment(commentRequest.getContent());
        Optional<Article> article = articleRepository.findById(article_id);
        UserEntity user = userRepository.findByUsername(commentRequest.getUsername());
        if (article.isPresent()) {
            comment.setArticle(article.get());
            comment.setUser(user);
            commentRepository.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(comment);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<Comment> update(Long article_id, Long comment_id, CommentRequest commentRequest) {
        Optional<Article> article = articleRepository.findById(article_id);
        if (article.isPresent()) {
            Optional<Comment> comment = commentRepository.findById(comment_id);
            UserEntity user = userRepository.findByUsername(commentRequest.getUsername());
            if (comment.isPresent() && user == comment.get().getUser()) {
                comment.get().setContent(commentRequest.getContent());
                comment.get().setUpdatedAt(new Date());
                commentRepository.save(comment.get());
                return ResponseEntity.status(HttpStatus.OK).body(comment.get());
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<Void> destroy(Long article_id, Long comment_id) {
        Optional<Article> article = articleRepository.findById(article_id);
        if (article.isPresent()) {
            Optional<Comment> comment = commentRepository.findById(comment_id);
            if (comment.isPresent()) {
                commentRepository.delete(comment.get());
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
