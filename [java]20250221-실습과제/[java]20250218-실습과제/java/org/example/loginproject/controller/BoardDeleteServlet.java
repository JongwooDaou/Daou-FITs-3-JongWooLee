package org.example.loginproject.controller;

import org.example.loginproject.service.BoardService;
import org.example.loginproject.vo.BoardVO;
import org.example.loginproject.vo.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/delete")
public class BoardDeleteServlet extends HttpServlet {
    BoardService boardService = new BoardService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        BoardVO board = boardService.selectBoardByID(id);

        HttpSession session = req.getSession();
        MemberVO member = (MemberVO) session.getAttribute("member");
        if (member != null && member.getID().equals(board.getMemberId())) {
            boardService.deleteBoardByID(id);
        } else {
            resp.sendRedirect("loginError.html");
        }

    }
}
