package org.example.loginproject.controller;

import org.example.loginproject.service.BoardService;
import org.example.loginproject.service.CommentService;
import org.example.loginproject.service.LikeService;
import org.example.loginproject.vo.BoardVO;
import org.example.loginproject.vo.CommentVO;
import org.example.loginproject.vo.UserVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/board/*") // /board로 시작하는 모든 요청 처리
public class BoardServlet extends HttpServlet {
    private BoardService boardService = new BoardService();
    private CommentService commentService = new CommentService();
    private LikeService likeService = new LikeService();



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo(); // "/list", "/view", "/write" 등
        System.out.println(action+"GET 접속");

        HttpSession session = req.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        //  로그인 사용자 아닐 경우
        //  로그인 화면으로 돌려보냄
        if (user == null) {
            resp.sendRedirect("loginError.html");
            return;
        }


        //  로그인 사용자일 경우
        //      로그인 한 member 에 관한 정보와
        //      board 의 모든 내용을 담아 보낸 후
        //      list.jsp 보여 주기
        if (action.equals("/list")) {
            String keyword = req.getParameter("keyword"); //쿼리스트링
            req.setCharacterEncoding("UTF-8");
            if(keyword==null){
                List<BoardVO> boardList = boardService.selectAllBoard();
                req.setAttribute("user", user);
                req.setAttribute("boardList", boardList);
                showBoardList(req, resp);
            } else {
                List<BoardVO> boardList = boardService.selectBoardByKeyword(keyword);
                req.setAttribute("user", user);
                req.setAttribute("boardList", boardList);
                showBoardList(req, resp);


            }


        }
        if (action.equals("/detail")) {
            String boardId = req.getParameter("boardId");
            BoardVO board = boardService.selectBoardById(boardId);
            List<CommentVO> comments = commentService.selectAllCommentByBoardId(boardId);
            boolean isLiked = likeService.isLiked(user.getId(), boardId);
            int likeCnt = likeService.countLikeByBoardId(boardId);
            System.out.println(likeCnt);
            req.setAttribute("likeCnt", likeCnt);
            req.setAttribute("isLiked", isLiked);
            req.setAttribute("board", board);
            req.setAttribute("comments", comments);
            showBoardDetail(req,resp);
        }
        if (action.equals("/post")) {
            req.setAttribute("user", user);
            showBoardPostForm(req, resp);
        }
        if (action.equals("/update")) {
            String boardId = req.getParameter("boardId");
            BoardVO board = boardService.selectBoardById(boardId);
            req.setAttribute("board", board);
            showBoardUpdateForm(req, resp);
        }
        if (action.equals("/delete")) {
            String boardId = req.getParameter("boardId");
            boardService.deleteBoardById(boardId);
            resp.sendRedirect("/board/list");
        }
    }

    private void showBoardList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(request, response);
    }
    private void showBoardDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/board/detail.jsp").forward(request, response);
    }
    private void showBoardPostForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/board/postForm.jsp").forward(request, response);
    }
    private void showBoardUpdateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/board/updateForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo(); // "/list", "/view", "/write" 등
        System.out.println(action+"GET 접속");

        HttpSession session = req.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
        //  로그인 사용자 아닐 경우
        //  로그인 화면으로 돌려보냄
        if (user == null) {
            resp.sendRedirect("loginError.html");
        }
        if(action.equals("/post")) {
            req.setCharacterEncoding("UTF-8");
            String title = req.getParameter("title");
            String content = req.getParameter("content");
            Long userId = user.getId();
            boardService.insertBoard(title,content,userId);
            resp.sendRedirect("/board/list");
        }
        if (action.equals("/update")) {
            req.setCharacterEncoding("UTF-8");
            String boardId = req.getParameter("boardId");
            String content = req.getParameter("content");
            String userId = req.getParameter("userId");
            System.out.println("boardId:"+boardId + "content:"+content+ "userId:"+userId);
            boardService.updateBoardById(boardId, content, userId);

            resp.sendRedirect("/board/list");
        }
    }
}
