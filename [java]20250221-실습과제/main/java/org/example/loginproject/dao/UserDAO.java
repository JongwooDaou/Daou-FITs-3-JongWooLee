package org.example.loginproject.dao;

import org.apache.ibatis.session.SqlSession;
import org.example.loginproject.vo.UserVO;

public class UserDAO {
    private SqlSession sqlSession;

    public UserDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public UserVO selectUserById(String username) {
        UserVO user;
        try{

            user = sqlSession.selectOne("User.selectUserByUsername", username);
            return user;
        }catch (Exception e){
            System.out.println(e.getMessage());;
        }
        return null;
    }
}
