package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioFormat.Encoding;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dto.Board;
import dto.Boardfile;
import dto.Reply;
import service.BoardService;
import service.BoardfileService;
import service.ReplyService;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardservice = new BoardService();
	private BoardfileService boardfileservice = new BoardfileService();
	private ReplyService replyservice = new ReplyService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getContextPath();
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		System.out.println("uri : " + uri);

		if (uri.contains("add")) {
			String saveDirectory = getServletContext().getInitParameter("savedir");
			System.out.println("서버 저장경로 : "+ saveDirectory);
			int size = 1024*1024*8; // 8메가바이트
			MultipartRequest multi = 
				new MultipartRequest(request, saveDirectory, size, "utf-8", 
					new DefaultFileRenamePolicy());
			
			// 게시판 글 추가
			// 정보
			String ip = request.getRemoteAddr();
			String userid = multi.getParameter("userid");
			String subject = multi.getParameter("subject");
			String content = multi.getParameter("content");
			// board 세팅
			Board board = new Board();
			board.setUserid(userid);
			board.setSubject(subject);
			board.setContent(content);
			board.setIp(ip);
			System.out.println(board);
			
			// 실제 올라간 파일 리스트
			 List<String> filenames = new ArrayList<>();
			 
			 // 파일 이름의 모음 - jsp 에서 읽어온 데이터
			 Enumeration<String> files = multi.getFileNames();
			 while(files.hasMoreElements()) { // 만약 파일이 있다면
				 String name = files.nextElement(); // String으로 하나씩 담기
				 
				 
				 String filename = multi.getFilesystemName(name); // 실제 저장된 이름 
				 System.out.println("filename 실제 저장된 이름 : "+ filename);
				 
				 if(filename != null)  filenames.add(filename); // 만약 담은 파일이 널이 아니라면
			 }
			 System.out.println("실제 올라갈 파일 이름 : "+filenames);
			boardservice.insert(board, filenames);
			response.sendRedirect(path+"/board/list?msg="+URLEncoder.encode("addSuc","utf-8"));
		}
		else if(uri.contains("list")) {
			// 리스트 목록 보여주기
			// 모든 조회가 이루어 지며 조회된 데이터를 list.jsp 로 보내준다.
			String findkey = request.getParameter("findkey");
			String findvalue = request.getParameter("findvalue");
			String curPage_s = request.getParameter("curPage");
			//curPage가 null일때 처리
			int curPage = 1;
			if (curPage_s != null)  
				curPage = Integer.parseInt(curPage_s) ;
			System.out.println("현재페이지:" + curPage);
			
			// 조회 조건 map 세팅 
			Map<String, Object> bmap = new HashMap<String, Object>();
			if(findkey== null || findkey.equals("")) findkey="subject";
			if(findvalue == null) findvalue = "";
			System.out.println("findkey : "+ findkey +"//"+ "findvalue : "+findvalue);
			bmap.put("findkey", findkey);
			bmap.put("findvalue", findvalue);
			bmap.put("curPage", curPage);
			System.out.println("bmap : "+ bmap);
			List<Board> blist = boardservice.selectList(bmap);
			System.out.println(blist);
			
			// forward 이동.
			request.setAttribute("blist", blist);
			request.setAttribute("bmap", bmap);
			
			request.getRequestDispatcher("/view/list.jsp").forward(request, response);
		}
		else if(uri.contains("detail")) {
			// 상세 정보창
			// bnum 을 파라미터로 가지고와서 정보조회 bnum = PK
			int bnum = Integer.parseInt(request.getParameter("bnum"));
		
			// 조회수 +1
			boardservice.update_readcnt(bnum);
			// 게시물 조회
			Board board = boardservice.selectOne(bnum);
			System.out.println(board);
			// 게시물 파일 조회
			List<Boardfile> blist = boardfileservice.selectList(bnum);
			System.out.println(blist);
			// 게시물 댓글 조회
			List<Reply> rlist = replyservice.selectList(bnum);
			System.out.println(rlist);
			 
			request.setAttribute("board", board);
			request.setAttribute("blist", blist);
			request.setAttribute("rlist", rlist);
			
			request.getRequestDispatcher("/view/detail.jsp").forward(request, response);
		}
		else if(uri.contains("modiform")) {
			// 수정 화면이동 
			int bnum = Integer.parseInt(request.getParameter("bnum"));
			// 1. board 조회
			Board board = boardservice.selectOne(bnum);
			
			// 2. boardfile 조회 
			List<Boardfile> blist = boardfileservice.selectList(bnum);
			
			
			request.setAttribute("board", board);
			request.setAttribute("blist", blist);
			
			request.getRequestDispatcher("/view/modify.jsp").forward(request, response);
		}
		else if(uri.contains("modify")) {
			// 수정 데이터 db 입력
			String saveDirectory = getServletContext().getInitParameter("savedir");
			System.out.println("서버 저장경로 : "+ saveDirectory);
			int size = 1024*1024*8; // 8메가바이트
			MultipartRequest multi = 
				new MultipartRequest(request, saveDirectory, size, "utf-8", 
					new DefaultFileRenamePolicy());
			
			//클라이언트 접속 ip정보
			String ip = request.getRemoteAddr();
			int bnum = Integer.parseInt(multi.getParameter("bnum"));
			String userid = multi.getParameter("userid");
			String subject = multi.getParameter("subject");
			String content = multi.getParameter("content");
			System.out.println("userid : " +userid + "subject"+ subject+"content : "+ content);
			// board 세팅
			Board board = new Board();
			board.setBnum(bnum);
			board.setUserid(userid);
			board.setSubject(subject);
			board.setContent(content);
			board.setIp(ip);
			System.out.println("baord : "+board);
			
			// 실제 올라간 파일 리스트
			 List<String> filenames = new ArrayList<>();
			 
			 // 파일 이름의 모음 - jsp 에서 읽어온 데이터
			 Enumeration<String> files = multi.getFileNames();
			 while(files.hasMoreElements()) { // 만약 파일이 있다면
				 String name = files.nextElement(); // String으로 하나씩 담기
				 
				 
				 String filename = multi.getFilesystemName(name); // 실제 저장된 이름 
				 System.out.println("filename 실제 저장된 이름 : "+ filename);
				 
				 if(filename != null)  filenames.add(filename); // 만약 담은 파일이 널이 아니라면
			 }
			 // 삭제할 파일명
			 String[] removefiles = multi.getParameterValues("removefile");
			 System.out.println("삭제할 파일명 : " +Arrays.toString(removefiles));
			 
			 // 서비스 호출
			 boardservice.update(board, filenames, removefiles);
			//detail로 이동
				response.sendRedirect(path+"/board/detail?bnum="+bnum);
		}else if(uri.contains("remove")) {
			// 게시글 삭제
			// 삭제는  삽입의 역순
			int bnum = Integer.parseInt(request.getParameter("bnum"));
			boardservice.delete_bnum(bnum);
			request.getRequestDispatcher("board/list?curPage=1&findkey=&findvalue=").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
