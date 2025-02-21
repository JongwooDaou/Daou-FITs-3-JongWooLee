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

    public BoardVO selectBoardById(int id) {
        BoardVO board = new BoardVO();
        board = sqlSession.selectOne("Board.selectBoardById", id);
        return board;
    }

    public List<BoardVO> selectAllBoard() {
        List<BoardVO> boardVOList = new ArrayList<>();
        try{
            boardVOList = sqlSession.selectList("Board.selectAllBoard");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return boardVOList;
    }

    public void deleteBoardById(int id) {
        try{
            sqlSession.delete("Board.deleteBoardById", id);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void insertBoard(BoardVO boardVO) {
        try{
            sqlSession.insert("Board.insertBoard", boardVO);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void updateBoardById(BoardVO boardVO) {
        try{
            sqlSession.update("Board.updateBoardById", boardVO);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public List<BoardVO> selectBoardByKeyword(String keyword) {
        List<BoardVO> boardVOList = new ArrayList<>();
        try{
            boardVOList = sqlSession.selectList("Board.selectBoardByKeyword", keyword);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return boardVOList;
    }
}
