<%@ page import="org.example.loginproject.vo.MemberVO" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<%--Scriptlet: 일반 자바 코드--%>
<%
    //  request 객체를 이용해서 "hoho" 로 명명한 member 객체를 가져오기
    //  member 객체의 name 가져오기
    //  JSP 에서는 request, response 이름이 정해져 있음
    MemberVO member = (MemberVO) request.getAttribute("hoho");
%>
<%--expression: 문자열을 단독으로 출력--%>

<body>
    <h1>로그인에 성공했습니다</h1>
    <%--expression: 문자열을 단독으로 출력--%>
    <h1><%=member.getName()%>님 환영합니다.</h1>
    <a href="/board">게시판 보기</a>


</body>
</html>
