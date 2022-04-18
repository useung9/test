package controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dto.Member;
import service.MemberService;

/**
 * Servlet implementation class MemberController
 */
@WebServlet("/boardmember/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MemberService memberservice = new MemberService();
		
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		System.out.println("uri : " + uri);
		if (uri.contains("join")) {
			String path = request.getContextPath();
			// 가입
			// multipart data 읽는 방식이 다름.
			// savedirectory = 파일저장경로(서버의 경로)
			String saveDirectory = "C:/Users/견디자/Desktop/files/savedir";

			// size - 업로드 파일 사이즈 제한.
			int size = 1024 * 1024 * 10;
			// DefaultFileRenamePolicy - 같은 이름ㅇ이ㅡ 파일 있을때 파일이름 변경
			MultipartRequest multi = new MultipartRequest(request, saveDirectory, size, "utf-8",
					new DefaultFileRenamePolicy());

			// MultipartRequest 를 이용한 데이터 받기
			String userid = multi.getParameter("userid");
			String passwd = multi.getParameter("passwd");
			String zipcode = multi.getParameter("zipcode");
			String addrload = multi.getParameter("addrload");
			String addrdetail = multi.getParameter("addrdetail");
			// 실제 서버에 등록된 파일 이름을 가져오기
			String filename = multi.getFilesystemName("photo");

			// 객체 생성
			Member member = new Member();
			member.setUserid(userid);
			member.setPasswd(passwd);
			member.setZipcode(zipcode);
			member.setAddrload(addrload);
			member.setAddrdetail(addrdetail);
			member.setFilename(filename);
			System.out.println(member);
			int cnt = memberservice.insert(member);

			response.sendRedirect(path + "/member/join.jsp?msg=" + URLEncoder.encode(cnt + " 건 추가"));
		} else if (uri.contains("login")) {
			System.out.println("login - from uri");
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
