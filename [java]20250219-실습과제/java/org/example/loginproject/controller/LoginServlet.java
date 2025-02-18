package org.example.loginproject.controller;

import org.example.loginproject.service.BookService;
import org.example.loginproject.service.MemberService;
import org.example.loginproject.vo.MemberVO;

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
    private MemberService memberService = new MemberService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String ID = req.getParameter("id");
        String password = req.getParameter("password");
        MemberVO member = memberService.login(ID, password);
        if (member != null) {
            // 로그인 성공

            HttpSession session = req.getSession();
            session.setAttribute("member", member);
            // JSP를 이용해서 VIEW 처리
            RequestDispatcher rd =
                    req.getRequestDispatcher("welcome.jsp");
            req.setAttribute("hoho", member);


            rd.forward(req, resp);
        } else {
            // 로그인 실패
            resp.sendRedirect("loginError.html");
        }







    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html>" + "<body>");
        out.println("하이하이");
        out.println("</body>");
        out.println("</html>");

        out.flush();
        out.close();
    }
}
