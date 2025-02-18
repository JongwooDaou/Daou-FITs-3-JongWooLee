package org.example.loginproject.controller;

import org.example.loginproject.service.BoardService;
import org.example.loginproject.vo.BoardVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/detail")
public class BoardDetailServlet extends HttpServlet {
    private BoardService boardService = new BoardService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        BoardVO board = boardService.selectBoardByID(id);
        RequestDispatcher rd =
                req.getRequestDispatcher(req.getContextPath()+"/boardDetail.jsp");
        System.out.println(board.getId());
        System.out.println(board.toString());

        req.setAttribute("board", board);
        rd.forward(req, resp);
    }
}
