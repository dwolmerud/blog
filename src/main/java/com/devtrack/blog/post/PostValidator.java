package com.devtrack.blog.post;

import com.devtrack.blog.common.BaseValidator;
import com.devtrack.blog.post.api.model.PostRequest;
import org.springframework.stereotype.Component;

@Component
public class PostValidator implements BaseValidator<PostRequest> {
    @Override
    public void validateOnSaveOrUpdate(PostRequest dto) {

    }
}

