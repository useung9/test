package service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import dao.MemberDAO;
import dto.Member;

public class MemberService {
	private MemberDAO mdao = new MemberDAO();
	public int insert(Member member) {

		return mdao.insert(member);
	}
	// salt를 램덤하게 만들기
		String saltmake() {
			String salt = null;

			try {
				// 난수생성 알고리즘
				SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
				byte[] bytes = new byte[16];
				sr.nextBytes(bytes); // 빈배열을 넣어주면 램덤한 값을 bytes에 만든다
				System.out.println(Arrays.toString(bytes));
				// 정수byte를 String으로 변경
				// Base64인코딩 : 아스키중 제어문자,일부 특수문자를 제외한 64개의 안전한 문자만 사용
				// 16byte -> 24byte
				salt = new String(Base64.getEncoder().encode(bytes));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return salt;
		}

		String sha256(String passwd, String salt) {
			// StringBuffer : String대신 사용, 속도(메모리) 효율적
			StringBuffer sb = new StringBuffer(); // 암호저장 변수
			try {
				// SHA-256 : 단방향 암호기법, 복호화 불가능, 256bit(16진수 64byte필요)
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(passwd.getBytes()); // 문자열을 바이트배열로 변경해서 전달
				md.update(salt.getBytes()); // 솔트를 추가

				byte[] data = md.digest(); // 암호화된 바이트 배열(32byte)
				System.out.println("암호화된 바이트 배열:" + Arrays.toString(data));
				System.out.println("배열길이:" + data.length);
				// 10진수를 16진수로 변환해서 sb변수에 추가
				for (byte b : data) {
					sb.append(String.format("%02x", b));
				}

			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return sb.toString();
		}
}
