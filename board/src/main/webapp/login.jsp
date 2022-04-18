<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/includeFile.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	//엔터를 누르면 실행
	function enterkey() {
		if (window.event.keyCode!=13) return;
		loginCheck();
	}

	//로그인 체크
	function loginCheck() {
		const userid = frmLogin.userid;
		const passwd = frmLogin.passwd;
		
		if (userid.value ==''){
			alert('아이디를 입력해 주세요');
			userid.focus();
		}else if (passwd.value ==''){
			alert('비밀번호를 입력해 주세요');
			passwd.focus();
			
		}else{
			frmLogin.action = '${path}/boardmember/login';
			frmLogin.method = 'post';
			frmLogin.submit();
		}
	}

</script>
</head>
<body>
	<h2>로그인</h2>
	<form name="frmLogin" action="" enctype="multipart/from-data">
		<table>
			<tr>
				<th>아이디</th>
				<!-- 쿠키의 값 세팅 -->
 				<td><input type="text" name="userid" value="${cookie.userid.value}"> </td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="password" name="passwd" onkeydown="enterkey()"> </td>
			</tr>
			<tr>
				<!-- 쿠키에 아이디가 있으면 checked -->
				<td colspan="2">
					아이디기억하기
					<!-- cookie.userid.value가 null이라면 -->
					<input type="checkbox" name="idsave" <c:out value="${empty cookie.userid.value?'':'checked'}" />> 
				</td>
				<%-- <td><input type="checkbox" name="idsave" <%=!userid.equals("")?"checked":""%>> </td> --%>
			</tr>

			<tr>
				<td align="center" colspan="2">
					<button type="button" onclick="loginCheck()">로그인</button> 
					<button type="reset">취소</button>
				</td>
			</tr>		
				
		</table>
	</form>
</body>
</html>