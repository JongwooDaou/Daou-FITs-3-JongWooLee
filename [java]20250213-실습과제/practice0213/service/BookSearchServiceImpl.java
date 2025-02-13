package practice0213.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import practice0213.dao.BookDAO;
import practice0213.vo.BookVO;

import java.util.HashMap;
import java.util.List;

public class BookSearchServiceImpl implements BookSearchService {
    private SqlSessionFactory sqlSessionFactory;
    private BookDAO bookDAO;

    public BookSearchServiceImpl() {

    }

    public BookSearchServiceImpl(SqlSessionFactory sqlSessionFactory, BookDAO bookDAO) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.bookDAO = bookDAO;
    }

    @Override
    public ObservableList<BookVO> findBookByTitle(String title) {
        ObservableList<BookVO> observableList = FXCollections.observableArrayList();
        List<BookVO> list = bookDAO.findBookByTitle(title);
        observableList.addAll(list);
        return observableList;
    }

    @Override
    public int deleteBookByISBN(String bisbn) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        sqlSession.commit();
        return bookDAO.deleteBookByIsbn(bisbn);
    }

    @Override
    public int insertBook(String bisbn, String btitle, String bprice, String bauthor) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);

        int intBprice = 0;
        try {
            intBprice = Integer.parseInt(bprice);
        } catch (NumberFormatException e) {
            return 0;
        }

        BookVO book = new BookVO(bisbn, btitle, intBprice, bauthor);
        sqlSession.commit();
        return bookDAO.insertBook(book);
    }

}
