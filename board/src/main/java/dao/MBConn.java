package dao;

import java.io.IOException;
import java.io.InputStream;

import javax.websocket.Session;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MBConn {
	public static SqlSession getsession() {
		SqlSession session = null;
		String resource = "mybatis/mybatisConfig.xml";
		try {
			InputStream is = Resources.getResourceAsStream(resource);
			// 팩토리 객체생성 - 세션을 만들어 낼수 있는 객체
			SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(is);
			session = sf.openSession();
			//System.out.println("session생성 성공");
		}catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}
}
