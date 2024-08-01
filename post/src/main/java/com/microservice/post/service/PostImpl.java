package com.microservice.post.service;

import com.microservice.post.config.RestTemplateConfig;
import com.microservice.post.entity.Post;
import com.microservice.post.payload.PostDto;
import com.microservice.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;


@Service
public class PostImpl implements PostService{
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RestTemplateConfig restTemplate;

    @Override
    public PostDto getPostWithComments(String postId) {
        Optional<Post> opPost = postRepository.findByPostId(postId);
        if(opPost.isPresent()) {
            ArrayList comments = restTemplate.getRestTemplate().getForObject("http://COMMENT-SERVICE/api/comment/" + postId, ArrayList.class);
            PostDto postDto = EntityToDto(opPost.get());
            postDto.setComments(comments);
            return postDto;
        }
        return null;
    }



    @Override
    public PostDto savePost(PostDto dto) {
        Post entity = DtoToEntity(dto);
        String postId = UUID.randomUUID().toString();
        entity.setId(postId);
        postRepository.save(entity);
        return EntityToDto(entity);
    }

    @Override
    public PostDto getPostById(String postId) {
        Optional<Post> opPost = postRepository.findByPostId(postId);
        if(opPost.isPresent()) {
            return EntityToDto(opPost.get());
        }
        return null;
    }

    public PostDto EntityToDto(Post entity){
        PostDto dto = new PostDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setDescription(entity.getDescription());
        return dto;
    }
    public Post DtoToEntity(PostDto dto){
        Post entity = new Post();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setDescription(dto.getDescription());
        return entity;
    }
}
