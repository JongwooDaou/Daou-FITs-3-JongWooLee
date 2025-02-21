<%@ page import="org.example.loginproject.vo.BoardVO" %>
<%@ page import="org.example.loginproject.vo.CommentVO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>게시글 상세보기</title>
    <script
            src="https://code.jquery.com/jquery-3.7.1.min.js"
            integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
            crossorigin="anonymous"></script>
    <script src="/js/detail.js"></script>
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

        /* 댓글 스타일 */
        .comments-section {
            margin-top: 30px;
            border-top: 2px solid #ddd;
            padding-top: 20px;
        }

        .comment {
            border-bottom: 1px solid #ddd;
            padding: 10px;
            margin-bottom: 10px;
        }

        .comment p {
            margin: 5px 0;
        }

        .comment-form {
            margin-top: 20px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f5f5f5;
        }

        .comment-form textarea {
            width: 100%;
            height: 80px;
            margin-bottom: 10px;
            padding: 5px;
        }

        .comment-form input[type="submit"] {
            padding: 8px 15px;
            border-radius: 5px;
            border: none;
            background-color: #28a745;
            color: white;
            cursor: pointer;
        }

        .comment-form input[type="submit"]:hover {
            background-color: #218838;
        }
    </style>
    <!-- jQuery 라이브러리 로드 -->

</head>
<body>

    <%
        BoardVO board = (BoardVO) request.getAttribute("board");
        List<CommentVO> comments = (List<CommentVO>) request.getAttribute("comments");
        boolean isLiked = (boolean) request.getAttribute("isLiked");
        int likeCnt = (int) request.getAttribute("likeCnt");
        if (board == null) {
    %>
    <h1>게시글을 찾을 수 없습니다.</h1>
    <%
    } else {
    %>
    <div class="container">
        <h1>게시글 상세보기</h1>

        <div class="post-info">
            <p><strong>제목:</strong> <%= board.getTitle() %>
            </p>
            <p><strong>작성자:</strong> <%= board.getUserId() %>
            </p>
            <p><strong>작성일:</strong> <%= board.getCreatedAt() %>
            </p>
        </div>

        <div class="content">
            <%= board.getContent().replace("\n", "<br>") %>  <%-- 줄바꿈 적용 --%>
        </div>
        <div class="like">
            <%

                if (isLiked) {
            %>
            <img class="likeBtn" src="/img/like.png" height="50" width="50">
            <p><%= likeCnt%></p>
            <%
            } else {
            %>
            <img class="unLikeBtn" src="/img/unlike.png" height="50" width="50">
            <p><%= likeCnt%></p>
            <%
                }
            %>
        </div>

        <div class="button-container">
            <a href="delete?boardId=<%= board.getId() %>" onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
            <a href="update?boardId=<%= board.getId() %>">수정</a>
            <a href="list">목록으로</a>
        </div>

        <div class="comments-section">
            <!-- 댓글 입력 폼 -->
            <div class="comment-form">
                <h3>댓글 작성</h3>
                <form id="commentForm">
                    <input type="hidden" name="boardId" id="boardId" value="<%= board.getId() %>">
                    <textarea name="content" id="commentContent" required placeholder="댓글을 입력하세요."></textarea>
                    <br>
                    <input type="submit" value="댓글 작성">
                </form>
            </div>

            <h2>댓글</h2>

            <!-- ✅ 항상 존재하는 #commentList div -->
            <div id="commentList">
                <%
                    if (comments == null || comments.isEmpty()) {
                %>
                <p>댓글이 없습니다.</p>
                <%
                } else {
                    for (CommentVO comment : comments) {
                %>
                <div class="comment">
                    <p><strong><%= comment.getUserId() %></strong> (<%= comment.getCreatedAt() %>)</p>
                    <p><%= comment.getContent().replace("\n", "<br>") %></p>
                    <button class="deleteCommentBtn" data-commentid="<%= comment.getId() %>">삭제</button>
                </div>

                <%
                        }
                    }
                %>
            </div> <!-- ✅ 항상 존재하는 #commentList -->
        </div>


    </div>
    </div>
    <%
        }
    %>

</body>
</html>
