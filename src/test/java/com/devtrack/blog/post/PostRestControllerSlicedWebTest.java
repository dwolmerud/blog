package com.devtrack.blog.post;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PostRestControllerSlicedWebTest {

    @MockBean
    private PostService postService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldHaveAPostsEndpoint() throws Exception {
        mockMvc.perform(get("/posts")).andDo(print()).andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    public void findAllPostsByTitle() {
        List<PostEntity> posts = new ArrayList<>();
        PostEntity post = PostEntity.builder()
                .id(1L)
                .title("test")
                .bodyText("text")
                .postedAt(Instant.now())
                .headerImageUrl("url").build();
        posts.add(post);
        when(postService.findAllByTitle(anyString())).thenReturn(posts);
        when(modelMapper.map(post, PostDTO.class)).thenReturn(PostDTO.builder()
                .id(1L)
                .title("test")
                .bodyText("text")
                .postedAt(Instant.now())
                .headerImageUrl("url").build());

        mockMvc.perform(get("/posts?title=test")).
                andExpect(jsonPath("$.length()").value(1)).
                andExpect(content().string(containsString("test")));
    }
}
