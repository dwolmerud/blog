package com.devtrack.blog.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostRestControllerTest {

    @Mock
    private PostService postService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PostRestController postRestController;

    @BeforeEach
    void setUp() {
    }

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
