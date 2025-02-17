package org.example.bookSearchServlet.dao;

import org.apache.ibatis.session.SqlSession;
import org.example.bookSearchServlet.vo.BookVO;

import java.util.ArrayList;
import java.util.List;

public class BookSearchDAO {
    private final SqlSession sqlSession;

    public BookSearchDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    public List<BookVO> searchBooks(String keyword) {
        List<BookVO> bookVOList = new ArrayList<>();
        bookVOList = sqlSession.selectList("BookSearchServlet.MyBook.selectBookByTitle", keyword);
        return bookVOList;
    }

    public BookVO searchBookByIsbn(String bisbn) {
        return sqlSession.selectOne("BookSearchServlet.MyBook.selectBookByISBN", bisbn);
    }
}
