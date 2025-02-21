package org.example.loginproject.controller.api;

import com.google.gson.Gson;
import org.example.loginproject.service.CommentService;
import org.example.loginproject.vo.CommentVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/comment/*")
public class RestCommentServlet extends HttpServlet {
    private final CommentService commentService = new CommentService();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CommentVO> commentVOList = commentService.selectAllComment();
        String json = gson.toJson(commentVOList);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}
