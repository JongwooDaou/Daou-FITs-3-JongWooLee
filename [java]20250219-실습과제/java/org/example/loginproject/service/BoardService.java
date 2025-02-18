package org.example.loginproject.service;

import org.apache.ibatis.session.SqlSession;
import org.example.loginproject.dao.BoardDAO;
import org.example.loginproject.myBatis.MyBatisFactory;
import org.example.loginproject.vo.BoardVO;

import java.util.List;

public class BoardService {
    public List<BoardVO> selectAllBoard() {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        BoardDAO boardDAO = new BoardDAO(sqlSession);
        return boardDAO.selectAllBoard();
    }

    public BoardVO selectBoardByID(String id) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        BoardDAO boardDAO = new BoardDAO(sqlSession);
        return boardDAO.selectBoardByID(id);
    }

    public void deleteBoardByID(String id) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        BoardDAO boardDAO = new BoardDAO(sqlSession);
        boardDAO.deleteBoardByID(id);
        sqlSession.commit();
    }
}
