package com.devtrack.blog.post.api;

import com.devtrack.blog.common.ErrorResponse;
import com.devtrack.blog.post.service.PostService;
import com.devtrack.blog.post.api.model.PostRequest;
import com.devtrack.blog.post.db.PostEntity;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@Configuration
@Slf4j
public class PostRestController {
    private final PostService postService;
    private final ModelMapper modelMapper;

    public PostRestController(PostService postService, ModelMapper modelMapper) {
        this.postService = postService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public Iterable<PostRequest> findAll() {
        List<PostEntity> posts = postService.findAll();

        return posts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(params = "title")
    public List<PostRequest> findAllByTitle(String title) {
        List<PostEntity> posts;
        try {
            posts = postService.findAllByTitle(title);
        } catch (PostNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post Not Found", ex);
        }

        log.info("Found {} number of posts including the title {}", posts.size(), title);
        return posts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public PostRequest findById(Long id) {
        PostEntity post;
        try {
            if(id == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Post Id");
            }
            post = postService.findById(id);
        } catch (PostNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Post Id", ex);
        }
        log.info("Found post with id {}", id);
        return convertToDto(post);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostRequest create(@RequestBody @Valid PostRequest postRequest) {
        return convertToDto(postService.create(postRequest));
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<PostRequest> update(@PathVariable Long id, String title, String text) {
        PostEntity post;
        try {
            post = postService.update(id, title, text);
        } catch (PostNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Post Id", ex);
        }
        PostRequest postRequest = convertToDto(post);
        return new ResponseEntity<>(postRequest, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        try {
           postService.delete(id);
        } catch (PostNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide correct Post Id", ex);
        }
        log.info("Post successfully deleted: {}", id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    private PostRequest convertToDto(PostEntity post) {
        if (post == null) {
            return null;
        }
        return modelMapper.map(post, PostRequest.class);
    }
}
