<%@ page import="org.example.loginproject.vo.BoardVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>게시글 상세보기</title>
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
        .post-info {
            border: 1px solid #ddd;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            background-color: #f9f9f9;
        }
        .content {
            border: 1px solid #ddd;
            padding: 15px;
            min-height: 200px;
            background-color: #fff;
            border-radius: 5px;
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

    <%
        BoardVO board = (BoardVO) request.getAttribute("board");
        System.out.println(board.toString());
        if (board == null) {
    %>
    <h1>게시글을 찾을 수 없습니다.</h1>
    <%
    } else {
    %>
    <div class="container">
        <h1>게시글 상세보기</h1>

        <div class="post-info">
            <p><strong>제목:</strong> <%= board.getTitle() %></p>
            <p><strong>작성자:</strong> <%= board.getMemberId() %></p>
            <p><strong>작성일:</strong> <%= board.getCreatedAt() %></p>
        </div>

        <div class="content">
            <%= board.getContent().replace("\n", "<br>") %>  <%-- 줄바꿈 적용 --%>
        </div>

        <div class="button-container">
<%--            <a href="boardEdit.jsp?id=<%= board.getId() %>">수정</a>--%>
            <a href="delete?id=<%= board.getId() %>" onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
            <a href="board">목록으로</a>
        </div>
    </div>
    <%
        }
    %>

</body>
</html>

