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


@WebServlet(value = "/book")
public class BookServlet extends HttpServlet {
    private final BookSearchService bookSearchService = new BookSearchService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String bisbn = req.getParameter("isbn");
        System.out.println(bisbn);
        BookVO bookVO = bookSearchService.searchBooksByIsbn(bisbn);

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Book Search Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>" + bookVO.getBtitle() + "</h1>");
        out.println("<p>" + bookVO.toString() + "</p>");
        out.println("</body>");
        out.println("</html>");

        out.flush();
        out.close();


    }
}
