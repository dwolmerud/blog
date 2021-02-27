package com.devtrack.blog.post.api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PostRequest {
    private Long id;
    private String title;
    private String bodyText;
    private Instant postedAt;

    @JsonIgnore
    private String headerImageUrl;
}
