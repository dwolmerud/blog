package com.devtrack.blog.post;

import java.util.List;

public interface PostService {
     List<PostEntity> findAll();

     List<PostEntity> findAllByTitle(String title);

     PostEntity findById(Long id);

     PostEntity create(PostDTO postDTO);

     PostEntity update(Long id, String title, String text);

     void delete(Long id);
}
