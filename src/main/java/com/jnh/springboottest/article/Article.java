package com.jnh.springboottest.article;

import com.jnh.springboottest.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
public class Article extends BaseEntity {
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;

    Article(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
