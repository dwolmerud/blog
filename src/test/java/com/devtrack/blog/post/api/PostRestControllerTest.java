package com.devtrack.blog.post.api;

import com.devtrack.blog.post.api.model.PostRequest;
import com.devtrack.blog.post.api.PostRestController;
import com.devtrack.blog.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class PostRestControllerTest {

    @InjectMocks
    private PostRestController postRestController;

    @Mock
    private PostService postService;

    @Test
    void callCreate() {
        PostRequest postRequest = createPostDTO();
        postRestController.create(postRequest);
        verify(postService, times(1)).create(postRequest);
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
