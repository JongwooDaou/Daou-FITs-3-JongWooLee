package practice0213.dao;

import org.apache.ibatis.session.SqlSession;
import practice0213.vo.BookVO;

import java.util.HashMap;
import java.util.List;

public class BookDAO {
    private SqlSession sqlSession;

    public BookDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    public List<BookVO> findBookByTitle(String title) {
        List<BookVO> list =
                sqlSession.selectList("practice0213.MyBook.selectBookByTitle", title);
        return list;
    }
    public int deleteBookByIsbn(String bisbn) {
        return sqlSession.delete("practice0213.MyBook.deleteBookByIsbn", bisbn);
    }

    public int insertBook(BookVO book) {
        return sqlSession.insert("practice0213.MyBook.insertBook", book);
    }
}
