package org.example.bookSearchServlet.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bookSearchServlet.dao.BookSearchDAO;
import org.example.bookSearchServlet.myBatisFactory.MyBatisFactory;
import org.example.bookSearchServlet.vo.BookVO;

import java.util.List;

public class BookSearchService {
    public List<BookVO> searchBooks(String keyword) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        System.out.println("sql session opened!");
        BookSearchDAO bookSearchDAO = new BookSearchDAO(sqlSession);
        System.out.println("sql 세션 생성");
        return bookSearchDAO.searchBooks(keyword);
    }

    public BookVO searchBooksByIsbn(String bisbn) {
        SqlSession sqlSession = MyBatisFactory.getSqlSessionFactory().openSession();
        BookSearchDAO bookSearchDAO = new BookSearchDAO(sqlSession);
        return bookSearchDAO.searchBookByIsbn(bisbn);
    }
}
