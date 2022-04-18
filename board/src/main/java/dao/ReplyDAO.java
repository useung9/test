package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import dto.Reply;

public class ReplyDAO {

	public int insert(Reply reply) {
		try (SqlSession session = MBConn.getsession()) {
			return session.insert("ReplyMapper.insert",reply);
		}
	}
	
	public List<Reply> selectList(int bnum){
	SqlSession session = MBConn.getsession();
		return session.selectList("ReplyMapper.selectList", bnum);
		
	}
	// 댓글 의댓글 수정
	public int update(Reply reply) {
		try (SqlSession session = MBConn.getsession()) {
			return session.delete("ReplyMapper.update", reply);
		}
	}
	
	// 글의 재수정
	public int update_restep(Reply reply) {
		try (SqlSession session = MBConn.getsession()) {
			return session.update("ReplyMapper.update_restep", reply);
		}
		
	}
	// 게시물한건에 대한 모든 댓글 삭제
	public int delete_bnum(int bnum) {
		try (SqlSession session = MBConn.getsession()) {
				return session.delete("ReplyMapper.delete_bnum", bnum);
		}
		
	}

	public Reply selectOne(int rnum) {
		try (SqlSession session = MBConn.getsession()) {
			return session.selectOne("ReplyMapper.selectOne",rnum);
		}
	}

	public int delete_rnum(int rnum) {
		try (SqlSession session = MBConn.getsession()) {
			return session.delete("ReplyMapper.delete_rnum", rnum);
					
		}
				
		
	}
}
