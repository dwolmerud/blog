package com.devtrack.blog.post;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebServiceClientTest
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
        PostDTO postDTO = createPostDTO();
        postService.create(postDTO);
        verify(postValidator).validateOnSaveOrUpdate(postDTO);
    }

    @Test
    void doCallSave() {
        PostDTO postDTO = createPostDTO();
        postService.create(postDTO);
        verify(postRepository).save(any(PostEntity.class));
    }

    private PostDTO createPostDTO() {
        return PostDTO.builder()
                .id(1L)
                .bodyText("test")
                .title("title")
                .headerImageUrl("image.jpg")
                .build();
    }
}
