package com.devtrack.blog.post.service;

import com.devtrack.blog.post.api.model.PostRequest;
import com.devtrack.blog.post.db.PostEntity;

import java.util.List;

public interface PostService {
     List<PostEntity> findAll();

     List<PostEntity> findAllByTitle(String title);

     PostEntity findByTitle(String title);

     PostEntity findById(Long id);

     PostEntity create(PostRequest postRequest);

     PostEntity update(Long id, String title, String text);

     void delete(Long id);
}
