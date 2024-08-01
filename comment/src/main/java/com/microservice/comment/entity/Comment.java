package com.microservice.comment.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @Column(name = "comment_id", nullable = false, unique = true)
    private String commentId;

    @Column(name = "post_id",  nullable = false)
    private String postId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable= false, unique = true)
    private String email;

    @Column(name = "body", nullable = false)
    private String body;

    public String getCommentIdId() {
        return commentId;
    }

    public void setCommentIdtId(String id) {
        this.commentId = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}