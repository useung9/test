package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import dto.Boardfile;

public class BoardfileDAO {
	public int insert(Boardfile boardfile) {
		try (SqlSession session = MBConn.getsession()) {
			return session.insert("BoardfileMapper.insert", boardfile);

		}

	}

	// bnum 에 해당하는 게시물에 대한 파일리스트
	public List<Boardfile> selectList(int bnum) {
		try (SqlSession session = MBConn.getsession()) {
			return session.selectList("BoardfileMapper.selectList", bnum);
		}

	}
	// bnum에 해당하는 boardfile 삭제
	public int delete_bnum(int bnum) {
		try (SqlSession session = MBConn.getsession()) {
			return session.delete("BoardfileMapper.delete_bnum", bnum);
		}
	}
	

		public int delete_bfnum(int bfnum) {
			try (SqlSession session = MBConn.getsession()) {
				return session.delete("BoardfileMapper.delete_bfum", bfnum);
			}
		}
	
}
