package org.example.loginproject.service;

import org.apache.ibatis.session.SqlSession;
import org.example.loginproject.dao.MemberDAO;
import org.example.loginproject.myBatis.MyBatisFactory;
import org.example.loginproject.vo.MemberVO;

public class MemberService {
    public MemberVO login(String id, String password) {
        MemberVO member = selectMemberByID(id);

        if(member != null && member.getPassword().equals(password)){
            return member;
        } else {
            return null;
        }
    }

    private MemberVO selectMemberByID(String id) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        MemberDAO memberDAO = new MemberDAO(sqlSession);
        return memberDAO.selectMemberByID(id);
    }
}
