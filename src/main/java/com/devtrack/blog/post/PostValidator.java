package com.devtrack.blog.post;

import com.devtrack.blog.common.BaseValidator;
import org.springframework.stereotype.Component;

@Component
public class PostValidator implements BaseValidator<PostDTO> {
    @Override
    public void validateOnSaveOrUpdate(PostDTO dto) {

    }
}

