package com.example.jspbasic.model;


import java.time.LocalDateTime;
//모델&저장소
public class Post {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;

    public Post(Long id, String title, String content, String author,LocalDateTime createdAt){
        this.id=id; this.title = title; this.content = content; this.author=author; this.createdAt=createdAt;
    }

    public Long getId(){return id;}
    public  String getTitle(){return title;}
    public String getContent(){return content;}
    public String getAuthor(){return author;}
    public LocalDateTime getCreatedAt(){return createdAt;}



}
