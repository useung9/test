package service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import dao.BoardDAO;
import dao.BoardfileDAO;
import dao.MBConn;
import dao.ReplyDAO;
import dto.Board;
import dto.Boardfile;

public class BoardService {
	private BoardDAO bdao = new BoardDAO();
	private BoardfileDAO bfdao = new BoardfileDAO();
	private ReplyDAO rdao = new ReplyDAO();

	public void insert(Board board, List<String> filenames) {
		// 삽입은 부모 에서 자식 순서로 = board -> boardfile
		// 삭제는 자식에서 부모로 = boardfile -> board
		int cnt;
		System.out.println("board : " + board);
		// 1. 부모 삽입
		cnt = bdao.insert(board);
		System.out.println(cnt + "건 board 추가");
		System.out.println("service : board - " + board);
		System.out.println("service : filenames - "+ filenames);
		// 2. 자식 삽입
		for (String filename : filenames) {
			Boardfile boardfile = new Boardfile();
			boardfile.setBnum(board.getBnum());
			boardfile.setFilename(filename);
		//	System.out.println("boardfile : " + boardfile);
			cnt += bfdao.insert(boardfile);

			
		}
		System.out.println(cnt + " 건  boarfile 추가");
	}

	public List<Board> selectList(Map<String, Object> bmap) {
		// 1. 페이징 처리(sql문 조회를 위해)
		int perPage = 10;
		int curPage = (int) bmap.get("curPage"); // 현재 페이지
		int startnum = (curPage-1)*perPage+1; // 시작번호
		int endnum = startnum+perPage-1; // 끝 번호
		bmap.put("startnum", startnum);
		bmap.put("endnum", endnum);
		
		// 2. 페이징블럭 처리 (list.jsp 의 페이지 번호 구하기 
		// 2-1 전체 게시물수
		int totCnt = bdao.select_totalcnt(bmap);
		int totPage = totCnt/perPage; // 나눔
		if(totCnt > totPage) totPage++; // 나머지가 있다면  +1 페이지 늘림
		bmap.put("totPage", totPage);
		
		// 2-2 시작페이지, 끝 페이지 구하기
		int perBlock = 10;
		int startPage = curPage - ((curPage-1) % perBlock);
		int endPage = startPage + perBlock -1;
		
		// endPage 수정
		if(endPage > totPage) endPage = totPage;
		bmap.put("startPage", startPage);
		bmap.put("endPage", endPage);
		
		System.out.println("bmap : " + bmap);
		
		return bdao.selectList(bmap);
		
	}

	public Board selectOne(int bnum) {
			
		return bdao.selectOne(bnum); 
		
	}
	// 조회수 증가
	public void update_readcnt(int bnum) {
		bdao.update_readcnt(bnum);
		
	}

	public void update(Board board, List<String> filenames, String[] removefiles) {
		// 1. 게시물 수정
		bdao.update(board);
		
		System.out.println("게시물 수정 완료");
		// 2. 추가할 파일
		for(String file : filenames) {
			Boardfile boardfile = new Boardfile();
			boardfile.setBnum(board.getBnum());
			boardfile.setFilename(file);
			System.out.println("Error Check : "+boardfile);
			bfdao.insert(boardfile);
		}
		System.out.println("추가파일 완료 ");
		// 3. 파일 삭제
		if(removefiles == null) return ;
		for(String bfnum : removefiles) {
			bfdao.delete_bfnum(Integer.parseInt(bfnum));
		}
		System.out.println("boardfile 삭제 완료");
	}

	public void delete_bnum(int bnum) {
		// 삭제는 삽입의 역순
		// 1. 댓글 삭제(자식) 반드시 자식부터
		int cnt = rdao.delete_bnum(bnum);
		System.out.println("rdao 삭제");
		// 2. 게시물 파일삭제(자식)
		cnt += bfdao.delete_bnum(bnum);
		System.out.println("bfdao 삭제");
		// 3. 게시물 삭제
		cnt += bdao.delete_bnum(bnum);
		System.out.println("bdao 삭제");
	}

}
