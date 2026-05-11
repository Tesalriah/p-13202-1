package com.jnh.springboottest.member;

import com.jnh.springboottest.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
public class Member extends BaseEntity {
    @Column(unique = true)
    private String username;
    private String password;
    private String nick;

    public Member(String username, String password, String nick) {
        this.username = username;
        this.password = password;
        this.nick = nick;
    }
}