package com.devtrack.blog.post;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostValidator postValidator;

    public PostServiceImpl(PostRepository postRepository, PostValidator postValidator) {
        this.postRepository = postRepository;
        this.postValidator = postValidator;
    }

    public List<PostEntity> findAll() {
        return postRepository.findAll();
    }

    public List<PostEntity> findAllByTitle(String title) throws PostNotFoundException  {
        List<PostEntity> posts = postRepository.findAllByTitle(title);

        if(posts == null || posts.size() == 0) {
            log.error("Unable to find post entry in repository with title {}", title);
            throw new PostNotFoundException("Unable to find post entry in repository with title " + title);
        }

        return posts;
    }

    public PostEntity findById(Long id) throws PostNotFoundException  {
        Optional<PostEntity> postEntity = postRepository.findById(id);
        if(postEntity.isEmpty()) {
            throw new PostNotFoundException("Unable to find post entry in repository for id " + id);
        }
        return postEntity.get();
    }

    public PostEntity create(PostDTO postDTO) {
        postValidator.validateOnSaveOrUpdate(postDTO);
        PostEntity post = PostEntity.builder()
                .id(1L)
                .title(postDTO.getTitle())
                .bodyText(postDTO.getBodyText())
                .postedAt(Instant.now())
                .build();
        return postRepository.save(post);
    }

    public PostEntity update(Long id, String title, String text) throws PostNotFoundException  {
        Optional<PostEntity> postEntity = postRepository.findById(id);
        if(postEntity.isEmpty()) {
            throw new PostNotFoundException("Unable to find post entry in repository for id" + id);
        }
        PostEntity post = PostEntity.builder()
                .title(title)
                .bodyText(text)
                .postedAt(Instant.now())
                .build();
        return postRepository.save(post);
    }

    public void delete(Long id) throws ResponseStatusException {
        Optional<PostEntity> postEntity = postRepository.findById(id);
        if(postEntity.isEmpty()) {
            throw new PostNotFoundException("Unable to find post entry in repository for id" + id);
        }
        postRepository.delete(postEntity.get());
    }

}
