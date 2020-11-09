package com.devtrack.blog.post;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PostRestControllerDataJPATestIT {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void shouldFindAllPosts() {
        Iterable<PostEntity> posts = postRepository.findAll();

        int nOfCities = 1;
        assertThat(posts).hasSize(nOfCities);
    }

    @Test
    public void savePost() {
        PostEntity post = PostEntity.builder()
                .id(1L)
                .title("title")
                .bodyText("text")
                .postedAt(Instant.now())
                .headerImageUrl("url").build();

        postRepository.save(post);

        Assert.assertNotNull(post.getId());
    }


}
