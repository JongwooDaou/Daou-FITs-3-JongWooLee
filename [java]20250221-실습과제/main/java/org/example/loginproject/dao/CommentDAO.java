package org.example.loginproject.dao;

import org.apache.ibatis.session.SqlSession;
import org.example.loginproject.vo.CommentVO;

import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    private SqlSession sqlSession;

    public CommentDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<CommentVO> selectAllComment() {
        List<CommentVO> commentVOList = new ArrayList<CommentVO>();
        try{
            commentVOList = sqlSession.selectList("Comment.selectAllComment");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return commentVOList;
    }

    public boolean insertComment(CommentVO commentVO) {
        try{
            sqlSession.insert("Comment.insertComment", commentVO);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<CommentVO> selectAllCommentByBoardId(Long boardId) {
        List<CommentVO> comments = new ArrayList<>();
        try{
            comments = sqlSession.selectList("Comment.selectAllCommentByBoardId", boardId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return comments;
    }

    public boolean deleteCommentById(Long id) {
        try{
            sqlSession.delete("Comment.deleteCommentById", id);
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
