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

    public BoardVO selectBoardById(String id) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        BoardDAO boardDAO = new BoardDAO(sqlSession);
        int boardId = Integer.parseInt(id);
        return boardDAO.selectBoardById(boardId);
    }

    public void deleteBoardById(String id) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        BoardDAO boardDAO = new BoardDAO(sqlSession);
        int boardId = Integer.parseInt(id);
        boardDAO.deleteBoardById(boardId);
        sqlSession.commit();
    }

    public void insertBoard(String title, String content, Long id) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        BoardDAO boardDAO = new BoardDAO(sqlSession);
        BoardVO boardVO = new BoardVO(title, content, id);
        boardDAO.insertBoard(boardVO);
        sqlSession.commit();
    }

    public void updateBoardById(String boardId, String content, String userId) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        BoardDAO boardDAO = new BoardDAO(sqlSession);
        Long parsedBoardId = Long.parseLong(boardId);
        Long parsedUserId = Long.parseLong(userId);
        BoardVO boardVO = new BoardVO(parsedBoardId, content, parsedUserId);
        System.out.println(boardVO.getContent()+" "+boardVO.getId()+" "+boardVO.getUserId());
        boardDAO.updateBoardById(boardVO);
        sqlSession.commit();

    }

    public List<BoardVO> selectBoardByKeyword(String keyword) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        BoardDAO boardDAO = new BoardDAO(sqlSession);
        return boardDAO.selectBoardByKeyword(keyword);

    }

}
