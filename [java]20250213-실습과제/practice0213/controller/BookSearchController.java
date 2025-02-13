package practice0213.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import practice0213.mybatis.MybatisSessionFactory;
import practice0213.service.BookSearchService;
import practice0213.vo.BookVO;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class BookSearchController implements Initializable {
    private final BookSearchService bookSearchService;

    public BookSearchController(BookSearchService bookSearchService) {
        this.bookSearchService = bookSearchService;
    }

    @FXML
    public TableColumn<BookVO, String> isbnCol;
    @FXML
    private TableColumn<BookVO, String> titleCol;
    @FXML
    public TableColumn<BookVO, Integer> priceCol;
    @FXML
    public TableColumn<BookVO, String> authorCol;

    @FXML public TextField isbnText;
    @FXML public TextField titleText;
    @FXML public TextField priceText;
    @FXML public TextField authorText;
    @FXML public TextField searchText;

    @FXML public Button createBtn;
    @FXML public Button searchBtn;
    @FXML public Button deleteBtn;

    @FXML public TableView<BookVO> bookTbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("bisbn"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("btitle"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("bprice"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("bauthor"));

        searchBtn.setOnAction(event -> {
            String title = searchText.getText();
            ObservableList<BookVO> list = bookSearchService.findBookByTitle(title);
            bookTbl.setItems(list);

        });

        deleteBtn.setOnAction(event -> {
            String bisbn = bookTbl.getSelectionModel().getSelectedItem().getBisbn();
            int deletedRow = bookSearchService.deleteBookByISBN(bisbn);
            System.out.println(deletedRow+"행 제거");
        });

        createBtn.setOnAction(event -> {
            String bisbn = isbnText.getText();
            String btitle = titleText.getText();
            String bprice = priceText.getText();
            String bauthor = authorText.getText();

            int insertedRow = bookSearchService.insertBook(bisbn, btitle, bprice, bauthor);
            System.out.println(insertedRow+"행 추가");
        });
    }
}
