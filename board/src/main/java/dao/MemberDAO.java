package dao;

import org.apache.ibatis.session.SqlSession;

import dto.Member;

public class MemberDAO {

	public int insert(Member member) {
		SqlSession session = MBConn.getsession();
		return session.insert("MemberMapper.insert", member);
	}

}
