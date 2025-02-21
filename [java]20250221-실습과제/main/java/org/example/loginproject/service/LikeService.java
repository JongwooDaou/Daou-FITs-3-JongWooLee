package org.example.loginproject.service;

import org.apache.ibatis.session.SqlSession;
import org.example.loginproject.dao.LikeDAO;
import org.example.loginproject.myBatis.MyBatisFactory;
import org.example.loginproject.vo.LikeVO;

public class LikeService {
    public boolean isLiked(Long userId, String boardId) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        LikeDAO likeDAO = new LikeDAO(sqlSession);
        Long parsedBoardId = Long.parseLong(boardId);
        LikeVO likeVO = new LikeVO(userId, parsedBoardId);
        Boolean isLiked = likeDAO.isLiked(likeVO);
        return isLiked;
    }


    public void insertLikeByLike(LikeVO like) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        LikeDAO likeDAO = new LikeDAO(sqlSession);
        likeDAO.insertLikeByLike(like);
        sqlSession.commit();
    }

    public void deleteLikebyLike(LikeVO like) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        LikeDAO likeDAO = new LikeDAO(sqlSession);
        likeDAO.deleteLikeByLike(like);
        sqlSession.commit();
    }
    public int countLikeByBoardId(String boardId) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        LikeDAO likeDAO = new LikeDAO(sqlSession);
        Long parsedBoardId = Long.parseLong(boardId);
        return likeDAO.countLikeByBoardId(parsedBoardId);
    }
}
