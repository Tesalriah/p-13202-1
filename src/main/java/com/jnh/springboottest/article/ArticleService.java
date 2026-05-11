package com.jnh.springboottest.article;

import com.jnh.springboottest.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article write(String title, String content, Member member) {
        Article article = new Article(title, content, member);
        return articleRepository.save(article);
    }

    public Article readOne(int id) throws Exception {
        Optional<Article> article = articleRepository.findById(id);
        return article.orElse(null);
    }

    public List<Article> readAll(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return articleRepository.findAll();
        }
        return articleRepository.findByTitleContainingOrContentContaining(keyword, keyword);
    }

    public Article modify(int id, String title, String content) throws Exception{
        Optional<Article> op = articleRepository.findById(id);
        Article article = op.orElseThrow();
        article.update(title, content);

        return articleRepository.save(article);
    }

    public void remove(int id) throws Exception{
        articleRepository.deleteById(id);
    }

}
