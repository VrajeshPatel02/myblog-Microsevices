package com.microservice.post.service;

import com.microservice.post.payload.PostDto;

public interface PostService {
    PostDto savePost(PostDto dto);
    PostDto getPostById(String postId);

    PostDto getPostWithComments(String postId);
}
