package com.jnh.springboottest.Article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article write(String title, String content) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setCreateDate(LocalDateTime.now());
        return articleRepository.save(article);
    }

    public Article readOne(int id) throws Exception {
        Optional<Article> article = articleRepository.findById(id);
        return article.orElse(null);
    }

    public List<Article> readAll() {
        return articleRepository.findAllByOrderByIdDesc();
    }

    public Article modify(int id, String title, String content) throws Exception{
        Optional<Article> op = articleRepository.findById(id);
        Article article = op.orElseThrow();
        article.setTitle(title);
        article.setContent(content);

        return articleRepository.save(article);
    }
}
