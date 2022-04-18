package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dto.Reply;
import service.ReplyService;

/**
 * Servlet implementation class ReplyController
 */
@WebServlet("/reply/*")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ReplyService replyservice = new ReplyService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		String path = request.getContextPath();
		System.out.println("Reply uri : " + uri);

		if (uri.contains("add")) {
			int bnum = Integer.parseInt(request.getParameter("bnum"));
			int restep = Integer.parseInt(request.getParameter("restep"));
			int relevel = Integer.parseInt(request.getParameter("relevel"));
			String content = request.getParameter("content");
			System.out.println("test : "+bnum +"/"+restep+"/"+relevel+"/"+content);
			Reply reply = new Reply();
			reply.setBnum(bnum);
			reply.setRestep(restep);
			reply.setRelevel(relevel);
			reply.setContent(content);
			System.out.println("reply : "+ reply);
			replyservice.insert(reply);
			
			response.sendRedirect(path+"/board/detail?bnum="+bnum);
		}else if(uri.contains("modiform")) {
			int rnum = Integer.parseInt(request.getParameter("rnum"));
			// rnum 에 맞는 데이터 조회 
			Reply reply = replyservice.selectOne(rnum);
			
			// 수정할 내용을 jsp 로 보낸다.
			request.setAttribute("reply", reply);
			request.getRequestDispatcher("/view/replymodify.jsp").forward(request, response);
		}else if(uri.contains("modify")) {
			// jsp 에서 입력받은 데이터 db에 던지기
			int rnum = Integer.parseInt(request.getParameter("rnum"));
			String content = request.getParameter("content");
			Reply reply = new Reply();
			reply.setRnum(rnum);
			reply.setContent(content);
			System.out.println(reply);
			
			// 삽입
			replyservice.update(reply);
			
			// redirect board detail 로 이동
			int bnum = Integer.parseInt(request.getParameter("bnum"));
			response.sendRedirect(path+"/board/detail.jsp?bnum="+bnum);
			// 댓글에 대한 삭제 
		}else if(uri.contains("remove")) {
			// 댓글삭제
			int rnum = Integer.parseInt(request.getParameter("rnum"));
			replyservice.delete(rnum);
			
			int bnum = Integer.parseInt(request.getParameter("bnum"));
			response.sendRedirect(path+"/board/detail?bnum="+bnum );
			// 
			
			
			}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
