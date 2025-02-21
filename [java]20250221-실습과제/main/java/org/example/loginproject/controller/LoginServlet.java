package org.example.loginproject.controller;

import org.example.loginproject.service.BookService;
import org.example.loginproject.service.MemberService;
import org.example.loginproject.service.UserService;
import org.example.loginproject.vo.MemberVO;
import org.example.loginproject.vo.UserVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    private BookService bookService = new BookService();
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("id");
        String password = req.getParameter("password");
        UserVO user  = userService.login(username, password);
        if (user != null) {
            // 로그인 성공

            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            // JSP를 이용해서 VIEW 처리
            RequestDispatcher rd =
                    req.getRequestDispatcher("welcome.jsp");
            req.setAttribute("hoho", user);


            rd.forward(req, resp);
        } else {
            // 로그인 실패
            resp.sendRedirect("loginError.html");
        }







    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("loginForm.html");
        rd.forward(req, resp);
    }
}
