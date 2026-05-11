package com.jnh.springboottest.article;

import com.jnh.springboottest.member.Member;
import com.jnh.springboottest.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;

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
    public String create(@RequestParam String title, @RequestParam String content, Principal principal) {
        Member member = null;
        try{
            member = memberService.findByUsername(principal.getName());
        }catch (NullPointerException nullPointerException){
            return "article_create";
        }
        articleService.write(title, content, member);
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
    public String showModify(@PathVariable int id, Model model, Principal principal) {
        try {
            Article article = articleService.readOne(id);
            if (!article.getMember().getUsername().equals(principal.getName())) {
                return "redirect:/article/detail/" + id;
            }
            model.addAttribute("article", article);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "article_create";
    }

    @PostMapping("/modify/{id}")
    public String modify(@PathVariable int id, @RequestParam String title,
                         @RequestParam String content, Principal principal) {
        try {
            Article article = articleService.readOne(id);
            if (!article.getMember().getUsername().equals(principal.getName())) {
                return "redirect:/article/detail/" + id;
            }
            articleService.modify(id, title, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/article/detail/" + id;
    }

    @PostMapping("/remove/{id}")
    public String remove(@PathVariable int id, Principal principal) {
        try {
            Article article = articleService.readOne(id);
            if (!article.getMember().getUsername().equals(principal.getName())) {
                return "redirect:/article/detail/" + id;
            }
            articleService.remove(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/article/list";
    }
}
