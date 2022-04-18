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
<h2> 게시물 등록</h2>
<script type="text/javascript">
	function add(e){
		e.preventDefault(); // 기본 이벤트 제거
		let userid = frmadd.userid;
		let subject = frmadd.subject;
		let content = frmadd.content;
		
		if(userid == ''){
			alert('작성자 입력해주세요');
			userid.focus();
			return ;
		}
		
		if(subject == ''){
			alert('제목 입력');
			subject.focus();
			return ;
		}
		if(content == ''){
			alert('내용 입력');
			content.focus();
			return ;
		}
		
		frmadd.submit();
	}
</script>

<form action = "${path}/board/add" method="post" enctype="multipart/form-data" name ="frmadd"> 
	<table>
		<tr>
			<th>작성자</th>
			<td><input type= "text" name = "userid"></td>
		</tr>
		<tr>
			<th>제목</th>
			<td><input type= "text" name = "subject"></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><input type= "text" name = "content"></td>
		</tr>
		<tr>
			<th>파일</th>
			<td>
				<input type= "file" name = "file1"><br>
				<input type= "file" name = "file2"><br>
				<input type= "file" name = "file3"><br>
				<input type= "file" name = "file4"><br>
				<input type= "file" name = "file5"><br>
			</td>
		</tr>
		<tr>			
			<td colspan="2" align="center">
				<button onclick="add(event)">추가</button>
				<button type= "reset">취소</button>
			</td>
		</tr>
		
	</table>

</form>
</body>
</html>