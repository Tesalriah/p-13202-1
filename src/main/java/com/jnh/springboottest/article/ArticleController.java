package com.jnh.springboottest.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/list")
    public String showList(Model model){
        List<Article> articleList = articleService.readAll();
        model.addAttribute("articleList", articleList);
        return "article_list";
    }

    @GetMapping("/create")
    public String showCreate(){

        return "article_create";
    }

    @PostMapping("/create")
    public String create(@RequestParam String title, @RequestParam String content){
        articleService.write(title, content);
        return "redirect:/article/list";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable int id, Model m){
        try{
            Article article = articleService.readOne(id);
            m.addAttribute("article", article);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "article_detail";
    }

    @GetMapping("/modify/{id}")
    public String showModify(@PathVariable int id, Model model){
        try{
            Article article = articleService.readOne(id);
            model.addAttribute(article);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "article_create";
    }

    @PostMapping("/modify/{id}")
    public String modify(@PathVariable int id, @RequestParam String title, @RequestParam String content){
        try{
            articleService.modify(id, title, content);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/article/detail/" + id;
    }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable int id){
        try{
            articleService.remove(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/article/list";
    }
}
