package com.example.jspbasic.repo;

import com.example.jspbasic.model.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryPostRepository implements PostRepository {
    private final Map<Long, Post> store = new ConcurrentHashMap<>();
    private  final AtomicLong seq = new AtomicLong();

    public List<Post> findAllDesc() {
        List<Post> list = new ArrayList<>(store.values());
        list.sort(Comparator.comparing(Post::getId).reversed());
        return list;

    }

    @Override
    public boolean update(long id, String title, String content, String author) {
        return false;
    }

    @Override
    public boolean delete(long id, String author) {
        return false;
    }

    public Post findById(long id){return store.get(id);}
    public Post save(String title, String content,String author){
        long id = seq.incrementAndGet();
        Post p = new Post(id,title,content,author, LocalDateTime.now());
        store.put(id,p);
        return p;
    }
}
