package com.devtrack.blog.post.db;

import com.devtrack.blog.post.db.PostEntity;
import com.devtrack.blog.post.db.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostRestRepositoryFullTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;


    @Test
    void shouldCreatePostAndFindItInDB() throws Exception {
        PostEntity post = PostEntity.builder()
                .id(1L)
                .title("title")
                .bodyText("bodyText")
                .postedAt(Instant.now())
                .headerImageUrl("url").build();


        mockMvc.perform(post("/posts", 1L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated());

        Optional<PostEntity> postEntity = postRepository.findByTitle("title");
        assertEquals("bodyText", postEntity.get().getBodyText());
    }

}