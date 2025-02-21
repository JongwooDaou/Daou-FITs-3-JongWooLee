package org.example.loginproject.controller;

import com.google.gson.Gson;
import org.example.loginproject.service.LikeService;
import org.example.loginproject.vo.LikeVO;
import org.example.loginproject.vo.UserVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/like/*")
public class LikeServlet extends HttpServlet {
    private final LikeService likeService = new LikeService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();

        HttpSession session = req.getSession();
        UserVO user = (UserVO) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect("loginError.html");
        }
        if (action.equals("/post")) {
            req.setCharacterEncoding("utf-8");

            // JSON 데이터를 읽고 파싱하기 (Gson 사용)
            BufferedReader reader = req.getReader();
            LikeVO like = gson.fromJson(reader, LikeVO.class);
            like.setUserId(user.getId());

            likeService.insertLikeByLike(like);

            // JSON 응답 반환 (Gson 사용)
            String jsonResponse = gson.toJson(new ResponseMessage(true));
            resp.getWriter().write(jsonResponse);
        }
        if (action.equals("/delete")) {
            req.setCharacterEncoding("utf-8");

            // JSON 데이터를 읽고 파싱하기 (Gson 사용)
            BufferedReader reader = req.getReader();
            LikeVO like = gson.fromJson(reader, LikeVO.class);
            like.setUserId(user.getId());

            likeService.deleteLikebyLike(like);

            // JSON 응답 반환 (Gson 사용)
            String jsonResponse = gson.toJson(new ResponseMessage(true));
            resp.getWriter().write(jsonResponse);
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
