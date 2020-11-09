package com.devtrack.blog.common;

public interface BaseValidator<D> {
    void validateOnSaveOrUpdate(D dto);
}
