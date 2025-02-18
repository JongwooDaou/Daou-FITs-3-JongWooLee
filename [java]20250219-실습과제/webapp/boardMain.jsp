<%@ page import="org.example.loginproject.vo.MemberVO" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.loginproject.vo.BoardVO" %>
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
        <h1>메인 게시판입니다</h1>

        <%-- 로그인한 사용자 환영 메시지 --%>
        <% MemberVO member = (MemberVO) request.getAttribute("member"); %>
        <h2><%= member.getName() %>님, 환영합니다.</h2>

        <%-- 게시글 목록 테이블 --%>
        <table>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
            </tr>
            <% List<BoardVO> boards = (List<BoardVO>) request.getAttribute("boardList");
                for (BoardVO board : boards) { %>
            <tr>
                <td><%= board.getId() %></td>
                <td><a href="detail?id=<%= board.getId() %>"><%= board.getTitle() %></a></td>
                <td><%= board.getMemberId() %></td>
                <td><%= board.getCreatedAt() %></td>
            </tr>
            <% } %>
        </table>

        <div class="button-container">
            <a href="boardCreate.jsp">글 작성</a>
        </div>
    </div>
</body>
</html>
