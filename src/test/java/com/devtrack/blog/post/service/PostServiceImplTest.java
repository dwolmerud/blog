package com.devtrack.blog.post.service;

import com.devtrack.blog.post.PostValidator;
import com.devtrack.blog.post.api.model.PostRequest;
import com.devtrack.blog.post.db.PostEntity;
import com.devtrack.blog.post.db.PostRepository;
import com.devtrack.blog.post.service.PostServiceException;
import com.devtrack.blog.post.service.PostServiceImpl;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class PostServiceImplTest {

    private static final Long POST_ID = 1L;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostValidator postValidator;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void givePostWhenFound() {
        PostEntity postEntity = PostEntity.builder()
                .id(1L)
                .postedAt(Instant.now())
                .bodyText("text")
                .title("title")
                .build();
        when(postRepository.findById(POST_ID)).thenReturn(Optional.of(postEntity));
        assertNotNull(postService.findById(POST_ID));
    }

    @Test
    void giveErrorWhenPostNotFound() {
        when(postRepository.findById(POST_ID)).thenThrow(new PostServiceException());
        assertThrows(PostServiceException.class, () -> postService.findById(POST_ID));
    }

    @Test
    void doValidateOnCreate() {
        PostRequest postRequest = createPostDTO();
        postService.create(postRequest);
        verify(postValidator).validateOnSaveOrUpdate(postRequest);
    }

    @Test
    void doCallSave() {
        PostRequest postRequest = createPostDTO();
        postService.create(postRequest);
        verify(postRepository).save(any(PostEntity.class));
    }

    private PostRequest createPostDTO() {
        return PostRequest.builder()
                .id(1L)
                .bodyText("test")
                .title("title")
                .headerImageUrl("image.jpg")
                .build();
    }
}
