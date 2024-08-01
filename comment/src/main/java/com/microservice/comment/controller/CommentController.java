package com.microservice.comment.controller;
import com.microservice.comment.payload.CommentDto;
import com.microservice.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comment")
public class CommentController {
    @Autowired
    private CommentService service;
    @PostMapping("/addComment")
    public ResponseEntity<?> saveComment(@RequestBody CommentDto comment){
        CommentDto commentDto = service.saveComment(comment);
        if(commentDto != null){
            return new ResponseEntity<>(commentDto, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Post Does not exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getAllCommentsByPostId(@PathVariable String postId){
        List<CommentDto> comments = service.getCommentsByPostId(postId);
        if(comments!=null){
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }
        return new ResponseEntity<>("Post does not exist", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
