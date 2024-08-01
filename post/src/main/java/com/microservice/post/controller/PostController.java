package com.microservice.post.controller;

import com.microservice.post.payload.PostDto;
import com.microservice.post.service.PostService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @PostMapping("/addpost")
    public ResponseEntity<?> savePost(@RequestBody PostDto dto){
        PostDto posted = postService.savePost(dto);
        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostByPostId(@PathVariable String postId) {
        PostDto postById = postService.getPostById(postId);
        if(postById != null){
            return new ResponseEntity<>(postById, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{postId}/comments")
    @CircuitBreaker(name="commentBreaker", fallbackMethod="commentFallback")
    public ResponseEntity<?> getPostWithComments(@PathVariable String postId){
        PostDto postWithComments = postService.getPostWithComments(postId);
        if(postWithComments!=null){
            return new ResponseEntity<>(postWithComments, HttpStatus.OK);
        }
        return new ResponseEntity<>("Post does not exist", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> commentFallback(@PathVariable String postId, Throwable ex){
        System.out.println("Fallback is wxecuted because serice is down" + ex.getMessage());
        ex.printStackTrace();
        PostDto dto = new PostDto();
        dto.setId("123");
        dto.setTitle("Service down");
        dto.setContent("Service is down, unable to retrieve comments.");
        dto.setDescription("Servie down");
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
