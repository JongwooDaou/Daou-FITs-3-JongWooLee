package org.example.bookSearchServlet.controller;

import org.example.bookSearchServlet.service.BookSearchService;
import org.example.bookSearchServlet.vo.BookVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(value = "/searchBook")
public class BookSearchServlet extends HttpServlet {
    private final BookSearchService bookSearchService = new BookSearchService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String bisbn = req.getParameter("bisbn");
        System.out.println(bisbn);
//        BookVO bookVO = bookSearchService.searchBooksByIsbn(bisbn);
//        System.out.println(bookVO.getBisbn());
//
//        resp.setContentType("text/html; charset=UTF-8");
//        PrintWriter out = resp.getWriter();
//
//        out.println("<html>" + "<body>");
//        out.println(bookVO.toString());
//        out.println("</body>");
//        out.println("</html>");
//
//        out.flush();
//        out.close();




    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String keyword = request.getParameter("keyword");
        String price = request.getParameter("price");

        System.out.println(keyword);
        System.out.println(price);

        List<BookVO> books = bookSearchService.searchBooks(keyword);

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html>" + "<body>");
        out.println("<h1>" + "검색 결과 입니다" + "</h1>");
        out.println("검색 키워드 : " + keyword + "<br>");
        out.println("price : " + price + "<br>");

        for (BookVO bookVO : books) {
            out.println("<li>");
            out.println("<a href='/searchBook?bisbn=" + bookVO.getBisbn() + "'>");
            out.println(bookVO.getBtitle() + " (ISBN: " + bookVO.getBisbn() + ")");
            out.println("</a>");
            out.println("</li>");
        }
        out.println("</body>");
        out.println("</html>");

        out.flush();
        out.close();

    }
}
