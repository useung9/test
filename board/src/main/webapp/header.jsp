<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/includeFile.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<header>
		<h2>회원관리</h2>
		<span><a href="${path}/info.member?userid=${sessionScope.userid}" >${sessionScope.userid}</a>님 환영합니다.</span>
		<a href="${path}/logout.member">로그아웃</a>
	</header>
	<hr>
	<nav>
		<a href="${path}/member/join.jsp">회원가입</a>
		<a href="${path}/list.member">리스트</a>
	</nav>
	<hr>	
</body>
</html>