package org.example.loginproject.service;

import org.apache.ibatis.session.SqlSession;
import org.example.loginproject.dao.UserDAO;
import org.example.loginproject.myBatis.MyBatisFactory;
import org.example.loginproject.vo.MemberVO;
import org.example.loginproject.vo.UserVO;

public class UserService {
    public UserVO login(String username, String password) {
        System.out.println("login start");
        UserVO user = selectUserByUsername(username);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

    private UserVO selectUserByUsername(String username) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        UserDAO userDAO = new UserDAO(sqlSession);
        return userDAO.selectUserById(username);
    }
}
