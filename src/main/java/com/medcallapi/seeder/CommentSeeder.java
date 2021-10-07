package com.medcallapi.seeder;

import com.medcallapi.entity.Article;
import com.medcallapi.entity.Comment;
import com.medcallapi.entity.UserEntity;
import com.medcallapi.repository.ArticleRepository;
import com.medcallapi.repository.CommentRepository;
import com.medcallapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CommentSeeder {
    private CommentRepository commentRepository;
    private ArticleRepository articleRepository;
    private UserRepository userRepository;

    public void seedCommentTable() {
        String[] usernames = {"admin", "doctor", "patient"};
        for (int i = 0; i < 10; i++) {
            Comment comment = new Comment("this comment number "+i+1);
            UserEntity user = userRepository.findByUsername(usernames[(int) Math.floor(Math.random()*usernames.length)]);
            Optional<Article> article = articleRepository.findById((long) Math.floor(Math.random()*3+1));
            if (article.isPresent()) {
                comment.setArticle(article.get());
                comment.setUser(user);
            }
            commentRepository.save(comment);
        }
    }

}
