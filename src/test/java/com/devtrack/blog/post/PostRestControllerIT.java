package com.devtrack.blog.post;

import com.devtrack.blog.BaseIT;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("integration")
public class PostRestControllerIT extends BaseIT {
    @LocalServerPort
    private int port;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setupTests() {
        postRepository.deleteAll();
    }

    @Test
    public void giveAllPosts() {
        var mockPosts = createPosts(2);

        // Using TestRestTemplate
        ResponseEntity<PostEntity[]> result = testRestTemplate.getForEntity("/posts", PostEntity[].class);
        List<PostEntity> postEntities = Arrays.asList(result.getBody());
        assertEquals(mockPosts.size(), postEntities.size());
        assertTrue(postEntities.stream().map(PostEntity::getTitle).collect(Collectors.toList()).containsAll(Arrays.asList
                (mockPosts.get(0).getTitle(), mockPosts.get(1).getTitle())));

        // Using Rest Assured
        PostEntity[] response = when()
                .get(createURLWithPort("/posts"))
                .then()
                .statusCode(200)
                .extract()
                .as(PostEntity[].class);
        assertEquals(mockPosts.get(0).getTitle(), response[0].getTitle());
    }

    @Test
    public void shouldCreatePost() throws JSONException {
        // Using TestRestTemplate
        PostEntity postEntity = PostEntity.builder()
                .id(1L)
                .title("My first post")
                .bodyText("This post is a great one")
                .build();

        assertEquals(0, postRepository.findAll().size());

        ResponseEntity<PostEntity> result = testRestTemplate.postForEntity("/posts", postEntity, PostEntity.class);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals("My first post", result.getBody().getTitle());
        assertEquals(1, postRepository.findAll().size());
        assertEquals("My first post", postRepository.findAll().get(0).getTitle());


        // Using Rest Assured
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "My second post"); // Cast
        requestParams.put("bodyText", "This post is also a great one");

        PostEntity response = given()
                .contentType(ContentType.JSON)
                .body(requestParams.toString())
                .post(createURLWithPort("/posts"))
                .then()
                .extract()
                .as(PostEntity.class);

        assertEquals("My second post", response.getTitle());
        assertEquals(2, postRepository.findAll().size());
        assertEquals("My second post", postRepository.findAll().get(1).getTitle());
    }

    private List<PostEntity> createPosts(int numberOfPosts) {
        List<PostEntity> postEntities = new ArrayList<>();

        IntStream.range(0, numberOfPosts).forEach(p -> {
            PostEntity postEntity = PostEntity.builder()
                    .id(1L)
                    .title("post title " + p)
                    .bodyText("text " + p)
                    .postedAt(Instant.now())
                    .build();
            postRepository.save(postEntity);
            postEntities.add(postEntity);
        });
        return postEntities;
    }

    @Test
    public void giveAllPostContainingAGivenTitle() {
        var mockPosts = createPosts(2);

        // Using Rest Assured
        PostDTO[] response = given()
                .queryParam("title", "post title")
                .when()
                .get(createURLWithPort("/posts"))
                .then()
                .statusCode(200)
                .extract()
                .as(PostDTO[].class);

        assertEquals(2, response.length);
        assertEquals(mockPosts.get(0).getTitle(), response[0].getTitle());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
