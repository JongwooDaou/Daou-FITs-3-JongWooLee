package org.example.loginproject.dao;

import org.apache.ibatis.session.SqlSession;
import org.example.loginproject.vo.BoardVO;

import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
    private SqlSession sqlSession;

    public BoardDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public BoardVO selectBoardByID(String id) {
        BoardVO board = new BoardVO();
        board = sqlSession.selectOne("Board.selectBoardByID", id);
        return board;
    }

    public List<BoardVO> selectAllBoard() {
        System.out.println("selectAllBoard dao");
        List<BoardVO> boardVOList = new ArrayList<>();
        try{
            boardVOList = sqlSession.selectList("Board.selectAllBoard");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        for(BoardVO boardVO : boardVOList){
            System.out.println(boardVO.toString());
        }

        return boardVOList;
    }

    public void deleteBoardByID(String id) {
        try{
            sqlSession.delete("Board.deleteBoardByID", id);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
