package com.devtrack.blog.post;

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
        PostDTO postDTO = createPostDTO();
        postRestController.create(postDTO);
        verify(postService, times(1)).create(postDTO);
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
