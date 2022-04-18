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
	<h2>상세정보창</h2>

	<table border="1">
		<tr>
			<th>게시번호</th>
			<th>제목</th>
			<td>내용</td>
			<th>작성자</th>
			<th>조회수</th>
			<th>작성일자</th>
			<c:if test="${!empty blist}">
				<th>등록 파일</th>
			</c:if>
		</tr>
		<tr>
			<td>${board.bnum}</td>
			<td>${board.subject}</td>
			<td>${board.content}</td>
			<td>${board.userid}</td>
			<td>${board.readcnt}</td>
			<td>${board.regidate}</td>
			<c:if test="${!empty blist}">
				<c:forEach var="list" items="${blist}">
					<td>${list.filename}</td>
				</c:forEach>
			</c:if>
		</tr>
		<tr align="right">
			<td>
				<button onclick="location.href='${path}/board/modiform?bnum=${board.bnum}'">수정</button>
				<button onclick="location.href='${path}/board/remove?bnum=${board.bnum}'">삭제</button>
				<button onclick="location.href='${path}/view/replyadd.jsp?bnum=${board.bnum}&restep=0&relevel=0'">댓글달기</button>
				<button onclick = "location.href='${path}/board/list'">리스트</button>
			</td>
		</tr>
	</table>
	<br>

	<h2>댓글창</h2>

	<c:forEach var="list" items="${rlist}">
	<table border="1">
		<tr>
			<th>댓글 번호</th>
			<th>내용</th>
			<th>등록일자</th>
			<th> </th>
		</tr>
		<tr>	
			<td>${list.rnum}
				<br> restep : ${list.restep} 
				<br>relevel : ${list.relevel} 
			<td>${list.content}</td> 
			<td>${list.regidate}</td>
			<td colspan="2" align="center">
			<button onclick="location.href='${path}/view/replyadd.jsp?bnum=${board.bnum}&restep=${list.restep}&relevel=${list.relevel}'">댓글</button>
			<button onclick="location.href='${path}/reply/modiform?rnum=${list.rnum}'">수정</button>
			<button onclick="location.href='${path}/reply/remove?rnum=${list.rnum}&bnum=${board.bnum}'">삭제</button>
			</td>
		</tr>

		</table>
		
	</c:forEach>

</body>
</html>