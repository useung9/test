<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/includeFile.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action = "" method ="post">
		<select>
			<option value="userid" <c:out value="${param.findkey == 'userid'?'selected':''}"/>>작성자</option>
			<option value="subject" <c:out value="${param.findkey == 'subject'?'selected':''}"/>>제목</option>
			<option value="content" <c:out value="${param.findkey == 'content'?'selected':''}"/>>내용</option>
		</select>
		<input type = "text" name = "findvalue" value="${param.findvalue}">
		<button>조회</button>
		<button type ="button" onclick="location.href='${path}/view/add.jsp'">추가</button>
	</form>
	
	<hr>
	<h3>리스트</h3>
	<table border="1">
		<tr>
			<th>번호</th>
			<th>작성자</th>
			<th>제목</th>
			<th>내용</th>
			<th>등록일자</th>
			<th>조회수</th>
		</tr>
		<tr>
		<c:forEach var="list" items="${blist}">
			<tr>
				<td>${list.bnum} </td>
				<td>${list.userid} </td>
				<td><a href="${path}/board/detail?bnum=${list.bnum}">${list.subject}</a> </td>
				<td>${list.content} </td>
				<td><fmt:formatDate value="${list.regidate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
				<td>${list.readcnt}</td>
			</tr>
		</c:forEach>
		</tr>
	</table>
	
	<hr>
	<!-- 페이징 -->
	
	<c:if test="${bmap.startPage != 1}">
		<a href="${path}/board/list?curPage=${bmap.startPage-1}&findkey=${bmap.findkey}&findvalue=${bmap.findvalue}">이전</a>
	</c:if>
	
	<c:forEach var = "i" begin="${bmap.startPage}" end="${bmap.endPage}">
			<!-- 현재페이지 일때-->
		<c:if test="${i==bmap.curPage}">
			<a id="active" href="${path}/board/list?curPage=${i}&findkey=${param.findkey}&findvalue=${param.findvalue}">${i}</a>
		</c:if>
		<!-- 현재페이지 아닐때 -->
		<c:if test="${i != bmap.curPage}">
			<a class = "notactive" href="${path}/board/list?curPage=${i}&findkey=${param.findkey}&findvalue=${param.findvalue}">${i}</a>
		</c:if>		 
	</c:forEach>
	<c:if test="${bmap.totPage > bmap.endPage}">
		<a href="${path}/board/list?curPage=${bmap.endPage+1}&findkey=${param.findkey}&findvalue=${param.findvalue}">다음</a>	
	</c:if>
</body>
</html>