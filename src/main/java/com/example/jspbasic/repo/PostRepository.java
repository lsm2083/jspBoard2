package com.example.jspbasic.repo;

import com.example.jspbasic.model.Post;
import java.util.List;

public interface PostRepository {
    Post save(String title, String content, String author);
    Post findById(long id);
    List<Post> findAllDesc();
    boolean update(long id, String title, String content, String author);
    boolean delete(long id, String author);
}
