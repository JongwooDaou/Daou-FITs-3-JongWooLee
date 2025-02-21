package org.example.loginproject.vo;

import java.sql.Timestamp; // 변경됨!

public class BoardVO {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Long likeCnt;
    private Long commentCnt;
    private Timestamp createdAt;  // Timestamp 수정됨
    private Timestamp updatedAt;  // Timestamp 수정됨
    private Timestamp deletedAt;  // Timestamp 수정됨

    // 기본 생성자
    public BoardVO() {
    }

    public BoardVO(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.likeCnt = 0L;
        this.commentCnt = 0L;
    }
    public BoardVO(Long id, String content, Long userId) {
        this.id = id;
        this.content = content;
        this.userId = userId;
    }


    public BoardVO(Long id, String title, String content, Long userId, Long likeCnt, Long commentCnt, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
    // 모든 필드를 포함한 생성자

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(Long likeCnt) {
        this.likeCnt = likeCnt;
    }

    public Long getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(Long commentCnt) {
        this.commentCnt = commentCnt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }
}