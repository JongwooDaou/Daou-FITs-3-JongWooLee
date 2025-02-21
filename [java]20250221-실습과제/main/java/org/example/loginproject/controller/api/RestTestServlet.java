package org.example.loginproject.controller.api;

import com.google.gson.Gson;
import org.example.loginproject.service.BoardService;
import org.example.loginproject.vo.BoardVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/test")
public class RestTestServlet extends HttpServlet {
    private final BoardService boardService = new BoardService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BoardVO> boardVOList = boardService.selectAllBoard();
        String json = gson.toJson(boardVOList);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);

        System.out.println(json);
        System.out.println("전송완료");
    }




}
