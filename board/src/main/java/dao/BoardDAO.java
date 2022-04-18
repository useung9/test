package dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import dto.Board;

public class BoardDAO {
	public int insert(Board board) {
		try (SqlSession session = MBConn.getsession()) {
		return	session.insert("BoardMapper.insert", board);
		}
		
	}

	public List<Board> selectList(Map<String, Object> bmap ) {
		try (SqlSession session = MBConn.getsession()) {
			return session.selectList("BoardMapper.selectList", bmap);
		}
		
		
	}
	// 전체 게시물수
	public int select_totalcnt(Map<String, Object> bmap) {
		try (SqlSession session = MBConn.getsession()) {
			return session.selectOne("BoardMapper.select_totalcnt", bmap);
					
		}
	}

	public Board selectOne(int bnum) {
		try (SqlSession session = MBConn.getsession()) {
			return session.selectOne("BoardMapper.selectOne",bnum);
		}
		
	}

	public void update_readcnt(int bnum) {
		try (SqlSession session = MBConn.getsession()) {
			session.update("BoardMapper.update_readcnt", bnum);
		}
		
		
	}

	public void update(Board board) {
		try (SqlSession session = MBConn.getsession()) {
			session.update("BoardMapper.update",board);
		}
	}

	public int delete_bnum(int bnum) {
		try (SqlSession session = MBConn.getsession()) {
			return session.delete("BoardMapper.delete_bnum",bnum);
		}
	}
}
