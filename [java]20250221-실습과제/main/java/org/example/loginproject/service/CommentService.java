package org.example.loginproject.service;

import org.apache.ibatis.session.SqlSession;
import org.example.loginproject.dao.CommentDAO;
import org.example.loginproject.myBatis.MyBatisFactory;
import org.example.loginproject.vo.CommentVO;

import java.util.List;

public class CommentService {
    public List<CommentVO> selectAllComment() {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        CommentDAO commentDAO = new CommentDAO(sqlSession);
        List<CommentVO> commentVOList = commentDAO.selectAllComment();
        return commentVOList;
    }

    public List<CommentVO> selectAllCommentByBoardId(String boardId) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        CommentDAO commentDAO = new CommentDAO(sqlSession);
        Long parsedBoardId = Long.parseLong(boardId);
        List<CommentVO> commentVOList = commentDAO.selectAllCommentByBoardId(parsedBoardId);
        return commentVOList;
    }

    public boolean insertComment(String content, String boardId, Long userId) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        CommentDAO commentDAO = new CommentDAO(sqlSession);
        Long parsedBoardId = Long.parseLong(boardId);
        Long parsedUserId = userId;

        CommentVO commentVO = new CommentVO(content, parsedUserId, parsedBoardId);
        boolean success = commentDAO.insertComment(commentVO);
        sqlSession.commit();
        return success;
    }

    public boolean deleteComment(Long id) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        CommentDAO commentDAO = new CommentDAO(sqlSession);
        boolean isDeleted = commentDAO.deleteCommentById(id);
        sqlSession.commit();
        return isDeleted;
    }
}
