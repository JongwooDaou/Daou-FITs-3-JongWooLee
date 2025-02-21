package org.example.loginproject.vo;

public class LikeVO {
    private Long userId;
    private Long boardId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public LikeVO(Long userId, Long parsedBoardId) {
        this.userId = userId;
        this.boardId = parsedBoardId;
    }
}
