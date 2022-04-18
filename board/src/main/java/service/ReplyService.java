package service;
import java.util.List;

import dao.ReplyDAO;
import dto.Reply;

public class ReplyService {
	private ReplyDAO rdao = new ReplyDAO();

	public List<Reply> selectList(int bnum) {

		return rdao.selectList(bnum);
	}

	public void insert(Reply reply) {
		// 댓글 순서 증가.
		reply.setRestep(reply.getRestep()+1);
		// 댓글 레벨증가
		reply.setRelevel(reply.getRelevel()+1);
		// 기존 댓글의 순서 수정
		rdao.update_restep(reply);
		
		//
		int cnt = rdao.insert(reply);
		System.out.println(cnt+"건 추가");
	}

	public Reply selectOne(int rnum) {
		return rdao.selectOne(rnum);
		}

	public void update_restep(Reply reply) {
		rdao.update_restep(reply);
	}
	public void update(Reply reply) {
		rdao.update(reply);
	}

	public void delete(int rnum) {
		rdao.delete_rnum(rnum);
		
	}	
	
}
