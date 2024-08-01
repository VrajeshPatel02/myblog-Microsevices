package com.microservice.comment.service;

import com.microservice.comment.config.RestTemplateConfig;
import com.microservice.comment.entity.Comment;
import com.microservice.comment.payload.CommentDto;
import com.microservice.comment.payload.Post;
import com.microservice.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CommentImpl implements CommentService{

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    RestTemplateConfig restTemplate;
    @Override
    public CommentDto saveComment(CommentDto commentDto) {
        Post postId = restTemplate.getRestTemplate().getForObject("http://POST-SERVICE/api/posts/" + commentDto.getPostId(), Post.class);
        if(postId != null){
            Comment entity = DtoToEntity(commentDto);
            commentRepository.save(entity);
            return EntityToDto(entity);
        }
        return null;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(String postId) {
        Post searched = restTemplate.getRestTemplate().getForObject("http://POST-SERVICE/api/posts/" + postId, Post.class);
        if(searched!=null){
            List<Comment> comments = commentRepository.findByPostId(postId);
            List<CommentDto> dto = comments.stream().map(c-> EntityToDto(c)).collect(Collectors.toList());
            return dto;
        }
        return  null;
    }

    public Comment DtoToEntity(CommentDto dto) {
        Comment entity = new Comment();
        entity.setCommentIdtId(UUID.randomUUID().toString());
        entity.setPostId(dto.getPostId());
        entity.setBody(dto.getBody());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        return entity;
    }
    public CommentDto EntityToDto(Comment entity){
        CommentDto dto = new CommentDto();
        dto.setCommentId(entity.getCommentIdId());
        dto.setBody(entity.getBody());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPostId(entity.getPostId());
        return dto;
    }
}
