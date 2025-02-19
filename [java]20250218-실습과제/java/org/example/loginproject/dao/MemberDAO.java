package org.example.loginproject.dao;

import org.apache.ibatis.session.SqlSession;
import org.example.loginproject.vo.MemberVO;

public class MemberDAO {
    private SqlSession sqlSession;

    public MemberDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public MemberVO selectMemberByID(String id) {
        return sqlSession.selectOne("Member.selectMemberByID", id);
    }
}
