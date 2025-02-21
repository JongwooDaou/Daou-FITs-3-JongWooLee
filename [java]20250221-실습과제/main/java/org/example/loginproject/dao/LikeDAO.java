package org.example.loginproject.dao;

import org.apache.ibatis.session.SqlSession;
import org.example.loginproject.vo.LikeVO;

public class LikeDAO {
    private final SqlSession sqlSession;

    public LikeDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Boolean isLiked(LikeVO likeVO) {
         if(sqlSession.selectOne("Like.selectLikeByLike", likeVO) != null)
             return true;
         else
             return false;
    }

    public void insertLikeByLike(LikeVO like) {
        try{
            sqlSession.insert("Like.insertLikeByLike", like);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteLikeByLike(LikeVO like) {
        try{
            sqlSession.delete("Like.deleteLikeByLike", like);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public int countLikeByBoardId(Long boardId) {
        try{
            return sqlSession.selectOne("Like.countLikeByBoardId", boardId);

        }catch (Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
}
