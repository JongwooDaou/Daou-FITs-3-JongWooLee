package org.example.loginproject.controller;

import org.example.loginproject.service.BoardService;
import org.example.loginproject.service.MemberService;
import org.example.loginproject.vo.BoardVO;
import org.example.loginproject.vo.MemberVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/board")
public class BoardServlet extends HttpServlet {
    private MemberService memberService = new MemberService();
    private BoardService boardService = new BoardService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //  쿠키 세션을 확인하여 로그인 한 사용자인 지 확인
        HttpSession session = req.getSession();
        MemberVO member = (MemberVO) session.getAttribute("member");

        //  로그인 사용자 아닐 경우
        //      로그인 화면으로 돌려보냄
        if (member == null) {
            resp.sendRedirect("loginError.html");
            return;
        }

        //  로그인 사용자일 경우
        //      로그인 한 member 에 관한 정보와
        //      board 의 모든 내용을 담아 보낸 후
        //      boardMain.jsp 보여 주기
        RequestDispatcher rd =
                req.getRequestDispatcher("boardMain.jsp");
        List<BoardVO> boardList = boardService.selectAllBoard();
        for (BoardVO board : boardList) {
            System.out.println(board.toString());
        }

        req.setAttribute("member", member);
        req.setAttribute("boardList", boardList);

        rd.forward(req, resp);

    }
}
