package com.devtrack.blog.post.db;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
@Builder(toBuilder = true)
public class PostEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long id;

    @Size(min=1, max=30, message="Title should have 1-30 characters")
    @NonNull
    private String title;

    @Size(min=1, max=800, message="Text should have 1-800 characters")
    @NonNull
    private String bodyText;

    @CreationTimestamp
    private Instant postedAt;

    private String headerImageUrl;
}
