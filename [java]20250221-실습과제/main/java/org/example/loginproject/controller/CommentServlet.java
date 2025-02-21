package org.example.loginproject.controller;

import com.google.gson.Gson;
import org.example.loginproject.service.CommentService;
import org.example.loginproject.vo.CommentVO;
import org.example.loginproject.vo.UserVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@WebServlet("/comment/*")
public class CommentServlet extends HttpServlet {
    private final CommentService commentService = new CommentService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();

        HttpSession session = req.getSession();
        UserVO user = (UserVO) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect("loginError.html");
        }
        if(action.equals("/delete")){
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            // JSON 데이터 읽기 (DTO 클래스 없이 Map으로 처리)
            BufferedReader reader = req.getReader();
            Gson gson = new Gson();
            Map<String, Object> requestData = gson.fromJson(reader, Map.class);

            // commentId를 가져와서 Long 타입으로 변환
            Long commentId = ((Number) requestData.get("commentId")).longValue();
            System.out.println(commentId+" "+requestData.get("commentId"));

            boolean success = commentService.deleteComment(commentId);

            // JSON 응답 반환
            resp.getWriter().write("{\"success\": " + success + "}");
        }
        if(action.equals("/post")){
            req.setCharacterEncoding("utf-8");

            // JSON 데이터를 읽고 파싱하기 (Gson 사용)
            BufferedReader reader = req.getReader();
            CommentData commentData = gson.fromJson(reader, CommentData.class);

            String content = commentData.getContent();
            String boardId = commentData.getBoardId();


            Long userId = user.getId();
            System.out.println("/post 실행");
            System.out.println("content: " + content + " boardId: " + boardId + " userId: " + userId);
            Boolean success = commentService.insertComment(content, boardId, userId);

            // JSON 응답 반환 (Gson 사용)
            String jsonResponse = gson.toJson(new ResponseMessage(success));
            resp.getWriter().write(jsonResponse);
        }
    }

    private static class CommentData {
        private String content;
        private String boardId;

        public String getContent() {
            return content;
        }

        public String getBoardId() {
            return boardId;
        }


    }
    // JSON 응답을 위한 DTO 클래스
    private static class ResponseMessage {
        private final boolean success;

        public ResponseMessage(boolean success) {
            this.success = success;
        }

        public boolean isSuccess() {
            return success;
        }
    }
}


