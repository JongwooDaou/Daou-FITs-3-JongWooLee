<%@ page import="org.example.loginproject.vo.BoardVO" %>
<%@ page import="org.example.loginproject.vo.UserVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>게시글 수정</title>
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
    .form-container {
      border: 1px solid #ddd;
      padding: 20px;
      border-radius: 5px;
      background-color: #f9f9f9;
    }
    .form-group {
      margin-bottom: 15px;
    }
    .form-group label {
      display: block;
      font-weight: bold;
      margin-bottom: 5px;
    }
    .form-group input, .form-group textarea {
      width: 100%;
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 5px;
    }
    .form-group input[readonly] {
      background-color: #f2f2f2;
      cursor: not-allowed;
    }
    .button-container {
      text-align: center;
      margin-top: 20px;
    }
    .button-container button, .button-container a {
      text-decoration: none;
      padding: 10px 15px;
      border-radius: 5px;
      background-color: #007bff;
      color: white;
      margin-right: 10px;
      border: none;
      cursor: pointer;
    }
    .button-container button:hover, .button-container a:hover {
      background-color: #0056b3;
    }
  </style>
</head>
<body>

  <div class="container">
    <h1>게시글 수정</h1>

    <%
      // 세션에서 로그인한 사용자 정보 가져오기
      UserVO user = (UserVO) request.getAttribute("user");

            // request에서 수정할 게시글 정보 가져오기
      BoardVO board = (BoardVO) request.getAttribute("board");
      if (board == null) {
    %>
    <h2>게시글을 찾을 수 없습니다.</h2>
    <%
    } else {
    %>
    <div class="form-container">
      <form action="/board/update" method="post">
        <!-- 게시글 ID (수정 시 필요) -->
        <input type="hidden" name="boardId" value="<%= board.getId() %>">

        <div class="form-group">
          <label for="title">제목:</label>
          <input type="text" id="title" name="title" value="<%= board.getTitle() %>" readonly>
        </div>

        <div class="form-group">
          <label for="userId">작성자:</label>
          <input type="text" id="userId" name="userId" value="<%= board.getUserId() %>" readonly>
        </div>

        <div class="form-group">
          <label for="content">내용:</label>
          <textarea id="content" name="content" rows="8" required><%= board.getContent() %></textarea>
        </div>

        <div class="button-container">
          <button type="submit">수정</button>
          <a href="list">목록으로</a>
        </div>
      </form>
    </div>
    <%
      }
    %>
  </div>

</body>
</html>
