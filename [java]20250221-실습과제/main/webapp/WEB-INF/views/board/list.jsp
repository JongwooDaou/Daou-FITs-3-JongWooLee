<%@ page import="org.example.loginproject.vo.MemberVO" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.loginproject.vo.BoardVO" %>
<%@ page import="org.example.loginproject.vo.UserVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>게시판</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .container {
            width: 60%;
            margin: auto;
        }
        h1 {
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        .button-container {
            text-align: center;
            margin-top: 20px;
        }
        .button-container a {
            text-decoration: none;
            padding: 10px 15px;
            border-radius: 5px;
            background-color: #007bff;
            color: white;
            margin-right: 10px;
        }
        .button-container a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <%
            List<BoardVO> boardList = (List<BoardVO>) request.getAttribute("boardList");
            UserVO user = (UserVO) request.getAttribute("user");
        %>

         로그인한 사용자 환영 메시지
        <h2><%= user.getName() %>님, 환영합니다.</h2>
        <form action="/board/list" method="GET" style="text-align: center; margin-top: 20px;">
            <input type="text" name="keyword" placeholder="검색어 입력" required>
            <button type="submit">검색</button>
        </form>

        게시글 목록 테이블
        <table>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>좋아요</th>
                <th>작성일</th>
            </tr>
            <%
                for (BoardVO board : boardList) { %>
            <tr>
                <td><%= board.getId() %></td>
                <td><a href="detail?boardId=<%= board.getId() %>"><%= board.getTitle() %></a></td>
                <td><%= board.getUserId() %></td>
                <td><%= board.getUserId() %></td>
                <td><%= board.getCreatedAt() %></td>
            </tr>
            <% } %>
        </table>

        <div class="button-container">
            <a href="/board/list">메인 페이지</a>
            <a href="post">글 작성</a>
        </div>
    </div>
</body>
</html>