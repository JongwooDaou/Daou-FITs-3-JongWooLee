package practice0213;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import practice0213.controller.BookSearchController;
import practice0213.dao.BookDAO;
import practice0213.mybatis.MybatisSessionFactory;
import practice0213.service.BookSearchService;
import practice0213.service.BookSearchServiceImpl;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            // MyBatis 세션 팩토리 가져오기
            SqlSessionFactory sqlSessionFactory = MybatisSessionFactory.getSqlSessionFactory();
            SqlSession sqlSession = sqlSessionFactory.openSession();

            // DAO 및 서비스 객체 생성
            BookDAO bookDAO = new BookDAO(sqlSession);
            BookSearchService bookSearchService = new BookSearchServiceImpl(sqlSessionFactory, bookDAO);

            // FXML 로더 설정
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/practice0213.fxml"));
            loader.setControllerFactory(param -> {
                if (param == BookSearchController.class) {
                    return new BookSearchController(bookSearchService); // 의존성 주입
                }
                try {
                    return param.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Book Search CRUD");
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
