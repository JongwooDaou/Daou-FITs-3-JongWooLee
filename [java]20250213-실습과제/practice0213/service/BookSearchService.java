package practice0213.service;

import javafx.collections.ObservableList;
import practice0213.vo.BookVO;

public interface BookSearchService {
    ObservableList<BookVO> findBookByTitle(String title);

    int deleteBookByISBN(String bisbn);

    int insertBook(String bisbn, String btitle, String bprice, String bauthor);
}
