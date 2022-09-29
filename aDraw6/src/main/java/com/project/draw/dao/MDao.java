package com.project.draw.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.project.draw.dto.BoardDto;
import com.project.draw.dto.CmBoardDto;
import com.project.draw.dto.ContentDto;
import com.project.draw.dto.DrawJoinDto;
import com.project.draw.dto.HeartDto;
import com.project.draw.dto.IBoardDto;
import com.project.draw.dto.MyIBoardDto;
import com.project.draw.dto.MyRaffleCalendarDto;
import com.project.draw.dto.RaffleCarouselImageDto;
import com.project.draw.dto.RaffleInfoUploadDto;
import com.project.draw.dto.RaffleReplyDto;
import com.project.draw.dto.RaffleReplyPageListDto;
import com.project.draw.dto.RaffleUploadDto;
import com.project.draw.dto.Servey1Dto;
import com.project.draw.dto.Servey2Dto;

public class MDao implements IDao {
	@Autowired
	private SqlSession sqlSession;
	
	/* ȸ������ */
	@Override
	public String join(DrawJoinDto dto) {
		String result = null;
		try {
			int res = sqlSession.insert("join",dto);
			System.out.println("join dao�� res��: " + res);
			if(res > 0) {
				result = "success";
			}
			else {
				result = "failed";
			}
		}
		catch(Exception e) {
			e.getMessage();
			result = "failed";
		}
		return result;
		
	}
	
	//�α��� ó��
	@Override
	public DrawJoinDto login(String pid) {
		System.out.println(pid);
		DrawJoinDto result = sqlSession.selectOne("login",pid);
		return result;
	}

	
//������ SNS --------------------------------------------
	
	//p1 ���û�ǰ �Խñ� �ҷ�����
		@Override
		public ArrayList<IBoardDto> p1item(String iModelName) {
			ArrayList<IBoardDto> dtos = (ArrayList)sqlSession.selectList("p1item", iModelName);
			return dtos;
		}
	//p1 �ű� �̹����Խñ� �ҷ�����	
		@Override
		public ArrayList<IBoardDto> iView1() {
			
			ArrayList<IBoardDto> dtos = (ArrayList)sqlSession.selectList("iView1");
			return dtos;
		}
	//p1 �α�Խù� �ҷ�����
		@Override
		public ArrayList<IBoardDto> hotIBoard() {
			ArrayList<IBoardDto> dtos = (ArrayList)sqlSession.selectList("hotIBoard");
			return dtos;
		}
	//p1 �α����� �ҷ�����
		@Override
		public ArrayList<MyIBoardDto> p1rUserView() {
			ArrayList<MyIBoardDto> dtos = (ArrayList)sqlSession.selectList("p1rUserView");
			return dtos;
		}
	//p1 ������� �ҷ�����
		@Override
		public ArrayList<MyIBoardDto> p1sUserView() {
			ArrayList<MyIBoardDto> dtos = (ArrayList)sqlSession.selectList("p1sUserView");
			return dtos;
		}

		
	//p2 �̹��� �Խñ� �󼼺���
		@Override
		public IBoardDto p2View(int iBoardNum) {
			IBoardDto dto = sqlSession.selectOne("p2View", iBoardNum);
			p2UpHit(iBoardNum);
			return dto;
		}
	//p2������ �ҷ�����
		public MyIBoardDto p2PView(String iId) {
			MyIBoardDto dto = sqlSession.selectOne("p2PView", iId);
			return dto;
		}
		
		//�Խñ� hit�� �ø���
		@Override
		public void p2UpHit(int iBoardNum) {
			sqlSession.update("p2UpHit", iBoardNum);
			System.out.println("��Ʈ����");
		}
		
	//p2��ۺҷ�����
		@Override
		public ArrayList<CmBoardDto> p2cmView(int cmIBNum) {
			ArrayList<CmBoardDto> dtos = (ArrayList)sqlSession.selectList("p2cmView", cmIBNum);
			return dtos;
		}
	//p2��ۻ����ϱ�
		@Override
		public void p2cmDelete(int cmNum) {
			int res = sqlSession.delete("p2cmDelete", cmNum);
			System.out.println("����� �����߽��ϴ�");
		}

	//p2���ƿ�Ȯ��
		@Override
		public HeartDto p2hCheck(HeartDto hdto) {
			HeartDto dto = sqlSession.selectOne("p2hCheck", hdto);
			return dto;
		}
	//p2���ƿ�Ŭ��
		@Override
		public void p2hUp(HeartDto hdto) {
			//HeartDto dto = new HeartDto(iBoardNum,iId);
			sqlSession.insert("p2hUp", hdto);
			p2UpHeart(hdto);
			System.out.println("p2hUp���ƿ���");
		}
	//p2���ƿ�����
		@Override
		public void p2hDown(int hNum) {
			sqlSession.delete("p2hDown",hNum);
			System.out.println("p2hDown���ƿ�����");
		}
		@Override
		public void p2UpHeart(HeartDto hdto) {
			sqlSession.update("p2UpHeart", hdto);
			System.out.println("�Խù� ���ƿ� +1��");
		}
		@Override
		public void p2DownHeart(int hiBoardNum) {
			sqlSession.update("p2DownHeart", hiBoardNum);
			System.out.println("�Խù� ���ƿ� -1��");
		}
		@Override
		public void p3TotalHUp(String iId) {
			sqlSession.update("p3TotalHUp", iId);
			System.out.println("����Ʈ�� +1");
		}
		@Override
		public void p3TotalHDown(String iId) {
			sqlSession.update("p3TotalHDown", iId);
			System.out.println("����Ʈ�� -1");
		}

	//p3 ���� ������ �ҷ�����
		@Override
		public MyIBoardDto p3View(String iId) {
			MyIBoardDto result = sqlSession.selectOne("p3View",iId);
			return result;
//			MyIBoardDto dto = sqlSession.selectOne("p3View", iId);
//			return dto;
		}
	//p3���ƿ��� �Խñ۸��� ����
		@Override
		public ArrayList<IBoardDto> p3IHeartList(String iId) {
			ArrayList<IBoardDto> dtos = (ArrayList)sqlSession.selectList("p3IHeartList", iId);
			return dtos;
		}	
	//p3 ���� ������ �̹�������Ʈ �ҷ�����
		@Override
		public ArrayList<IBoardDto> p3IList(String iId) {
			//IBoardDto dto = sqlSession.selectOne("p3IList", iId);
			ArrayList<IBoardDto> dtos = (ArrayList)sqlSession.selectList("p3IList",iId);
			return dtos;
		}	
	//p3my �������̹��� �����ϱ�
		@Override
		public String p3myPModi(MyIBoardDto dto) {
			String result;
			int res = sqlSession.update("p3myPModi", dto);
			
			if(res == 1) {
				result = "success";
				System.out.println("������ ������Ʈ ����");
			}
			else {
				result = "failed";
			}
			return result;
		}	
	//p3my ������ �ڱ�Ұ� �����ϱ�
		@Override
		public void p3myModi(MyIBoardDto dto) {
			sqlSession.update("p3myModi", dto);
		}	
	//p4 �Խñ� �ۼ��ϱ�()
		@Override
		public String p4WriteI(IBoardDto dto) {
			System.out.println("p4WriteI dao�޼��� ��û");
			System.out.println("dto:" + dto);
			System.out.println(dto.getiBoardNum() +dto.getiId()+ dto.getiModelName()+ dto.getiImagePath());
			String result;
			int res = sqlSession.insert("p4WriteI", dto);
			
			if(res == 1) {
				result = "success";
				System.out.println("�Խñ� �ۼ� ����");
			}
			else {
				result = "failed";
			}
			return result;
		}
	//p4 �Խñ� ���������� �ҷ�����
		@Override
		public IBoardDto p4modifyView(int iBoardNum) {
			IBoardDto dto = sqlSession.selectOne("p4modifyView", iBoardNum);
			return dto;
		}

	//p4 �Խñ� �����ϱ�
		@Override
		public void p4modify(IBoardDto dto) {
			sqlSession.update("p4modify", dto);
		}
	//p4 �Խñ� �����ϱ�
		@Override
		public void p4modifyDelete(int iBoardNum) {
			int res = sqlSession.delete("p4modifyDelete", iBoardNum);
		}
	//p4�Խñ� �ۼ��ϱ�
		@Override
		public void p2mentWrite(int cmIBNum, String cmId, String cmText) {
			System.out.println("cmIBNum: " +cmIBNum);
		CmBoardDto dto = new CmBoardDto(cmIBNum,cmId,cmText);
		System.out.println("dto: " +dto.getCmIBNum());
		sqlSession.insert("p2mentWrite", dto);
		}
		
	//p5�׷����ҷ�����
		@Override
		public ArrayList<Servey1Dto> p5View1() {
			System.out.println("mdao p5View1�޼���");
			ArrayList<Servey1Dto> dtos = (ArrayList)sqlSession.selectList("p5View1");
			return dtos;
		}
		@Override
		public ArrayList<Servey2Dto> p5View2() {
			System.out.println("mdao p5View2�޼���");
			ArrayList<Servey2Dto> dtos = (ArrayList)sqlSession.selectList("p5View2");
			return dtos;
		}
	/*p5�׷����ۼ��ϱ�(form�� �̿ϼ�)
		@Override
		public void p5Write(Servey1Dto dto) {
			System.out.println("p5Writeó��");
			int res = sqlSession.insert("p5Write", dto);
		}
		//�� ���ƿ� �� ���� ī��Ʈ �ذ����
		@Override
		public int p3TotalHeart(String iId) {
			int num = sqlSession.selectOne("p3TotalHeart", iId);
			System.out.println("daoNum" + num);
//			int num1 = sqlSession.selectOne("p3TotalHeart", iId);
//			String num = String.valueOf(num1);
//			System.out.println("String"+num1);
//			System.out.println("Int" + num);
			return num;
		}
	*/

		
		
//�̿��� ------------------------------------------------------------		
	
		public SqlSession getSqlSession() {
			return sqlSession;
		}
		public void setSqlSession(SqlSession sqlSession) {
			this.sqlSession = sqlSession;
		}
		@Override
		public String sell(ContentDto dto) {
			System.out.println("sell_dao");
			String result;
			int res = sqlSession.insert("sell",dto);
			if(res == 1) {
				result = "success";
			}
			else {
				result = "failed";
			}
			
			return result;
		}	
		@Override
		public ArrayList<ContentDto> listDao(){
			ArrayList<ContentDto> result = (ArrayList)sqlSession.selectList("listDao");
			return result;
		}
		@Override
		public ArrayList<ContentDto> nikeList(){
			ArrayList<ContentDto> result = (ArrayList)sqlSession.selectList("nikeList");
			return result;
		}
		@Override
		public ArrayList<ContentDto> adidasList(){
			ArrayList<ContentDto> result = (ArrayList)sqlSession.selectList("adidasList");
			return result;
		}
		@Override
		public ArrayList<ContentDto> newbalList(){
			ArrayList<ContentDto> result = (ArrayList)sqlSession.selectList("newbalList");
			return result;
		}
		@Override
		public ArrayList<ContentDto> unarmList(){
			ArrayList<ContentDto> result = (ArrayList)sqlSession.selectList("unarmList");
			return result;
		}
		
		@Override
		public ArrayList<ContentDto> newsort(){
			ArrayList<ContentDto> result = (ArrayList)sqlSession.selectList("newsort");
			return result;
		
		}
		@Override
		public ArrayList<ContentDto> oldsort(){
			ArrayList<ContentDto> result = (ArrayList)sqlSession.selectList("oldsort");
			return result;
		
		}
		@Override
		public ArrayList<ContentDto> lpricesort() {
			ArrayList<ContentDto> result = (ArrayList)sqlSession.selectList("lpricesort");
			return result;
		}
		@Override
		public ArrayList<ContentDto> hpricesort() {
			ArrayList<ContentDto> result = (ArrayList)sqlSession.selectList("hpricesort");
			return result;
		}
		@Override
		public void modify(ContentDto dto) {
			sqlSession.update("modify",dto);
			
		}
		@Override
		public ArrayList<ContentDto> apageList(String pageNo) {
			System.out.println("apageList");
			int page = Integer.parseInt(pageNo);
			int startNo = (page-1) * 4 +1;
			System.out.println("startNo : " + startNo);
			ArrayList<ContentDto> result = (ArrayList)sqlSession.selectList("apageList",startNo);
			return result;
		}
		
		
// �Լ��� LAFFEL----------------------------------------
		
		
		//���� ���ε�
		@Override
		public String raffleUpload(RaffleUploadDto rudto) {
			System.out.println("raffleUpload����");
			String result;
			int res = sqlSession.insert("raffleUpload", rudto);
			if(res == 1) {
				result = "success";
			}
			else {
				result = "failed";
			}
			return result;
		}
		
		//���� ĳ�μ����ε�
		@Override
		public String raffleCarouselUpload(RaffleCarouselImageDto rcidto) {
			System.out.println("raffleCarouselUpload dao ����");
			String result;
			int res = sqlSession.insert("raffleCarouselUpload", rcidto);
			if(res == 1) {
				result = "success";
			}
			else {
				result = "failed";
			}
			return result;
		}
		
		//���ø���Ʈ ����
		@Override
		public ArrayList<RaffleUploadDto> raffleList() {
			System.out.println("raffleList ���� ����");
			ArrayList<RaffleUploadDto> dtos = (ArrayList) sqlSession.selectList("raffleList");
			System.out.println(dtos);
			return dtos;
		}
		
		//���ø���Ʈ �����ϱ�
		@Override
		public void raffleListDelete(int ruId) {
			System.out.println("raffleListDelete dao����");
			sqlSession.delete("raffleListDelete", ruId);
		}

		//���� ������ My Raffle Calendar�� ����ϱ� 
		@Override
		public void addtoCalendar(MyRaffleCalendarDto dto) {
			System.out.println("addtoCalendar dao ����");
			sqlSession.insert("addtoCalendar", dto);
		}
		
		//���� ������ ����
		@Override
		public RaffleUploadDto raffleDetails(String ruCode) {
			System.out.println("raffleDetails dao ����");
			RaffleUploadDto dto = sqlSession.selectOne("raffleDetails", ruCode);
			return dto;
		}
		
		//���� ĳ�μ� ������ ����
		@Override
		public ArrayList<RaffleCarouselImageDto> raffleCarouselDetails(String ruCode) {
			System.out.println("raffleCarouselDetails dao ����");
			ArrayList<RaffleCarouselImageDto> rcidtos = (ArrayList) sqlSession.selectList("raffleCarouselDetails", ruCode);
			System.out.println(rcidtos);
			return rcidtos;
		}
		
		
		//���� �������� ���ε�
		@Override
		public void raffleInfoUpload(RaffleInfoUploadDto riudto) {
			System.out.println("raffleInfoUpload dao ����");
			sqlSession.insert("raffleInfoUpload", riudto);		
		}
		
		//���� �������� ����
		@Override
		public ArrayList<RaffleInfoUploadDto> RaffleInfoList(String ruCode) {
			System.out.println("RaffleInfoList Dao ����");
			System.out.println("RaffleInfoList Dao �� ruCode: " + ruCode);
			ArrayList<RaffleInfoUploadDto> riudto = (ArrayList)sqlSession.selectList("raffleInfoList", ruCode);
			return riudto;
		}
		
		//���� �������� ����
		@Override
		public void raffleInfoDelete(int id) {
			System.out.println("raffleInfoDelete Dao ����");
			sqlSession.delete("raffleInfoDelete", id);
		}	
		
		//���� ��� �ۼ��ϱ�
		@Override
		public void RaffleReplyWrite(String rUserId, String rContent, String rCode) {
			System.out.println("RaffleReplyWrite dao ����");
			RaffleReplyDto rdto = new RaffleReplyDto(rUserId, rContent, rCode);
			sqlSession.insert("RaffleReplyWrite", rdto);		
		}
		
		//���� ��� ����
		@Override
		public ArrayList<RaffleReplyDto> RaffleReplyList(String rCode) {
			System.out.println("RaffleReplyList dao ����");
			System.out.println("RaffleReplyList dao�� rCode: " + rCode);
			ArrayList<RaffleReplyDto> rrdtos = (ArrayList)sqlSession.selectList("RaffleReplyList", rCode);
			return rrdtos;
		}
		
		//���� ��� ���������̼�
		@Override
		public ArrayList<RaffleReplyDto> pagelist(String pageNo, String rCode) {
			System.out.println("pagelist dao ����");
			int page = Integer.parseInt(pageNo);
			int startNo = (page-1) * 10 + 1;
			System.out.println("pagelist dao�� startNo: " + startNo);
			System.out.println("pagelist dao�� rCode: " + rCode);
			
			RaffleReplyPageListDto rrpldto = new RaffleReplyPageListDto(rCode, startNo);
			
			ArrayList<RaffleReplyDto> result = (ArrayList) sqlSession.selectList("pagelist", rrpldto);
			
			System.out.println(result);
			
			return result;
		}
		
		
		//���� ��� ����
		@Override
		public void raffleReplyDelete(int rid) {
			System.out.println("raffleReplyDelete dao����");

			int res = sqlSession.delete("raffleReplyDelete", rid);
		}
		
		//���� Ķ���� ����
		@Override
		public ArrayList<MyRaffleCalendarDto> myRaffleCalendarList(String cId) {
			System.out.println("myRaffleCalendarList dao����");
			ArrayList<MyRaffleCalendarDto> list = (ArrayList) sqlSession.selectList("myRaffleCalendarList", cId);
			return list;
		}
		
		//���� Ķ���� ���� ����
		@Override
		public void myRaffleCalendarUpdate(MyRaffleCalendarDto dto) {
			System.out.println("myRaffleCalendarUpdate dao ����");
			sqlSession.update("myRaffleCalendarUpdate", dto);
		}
		
		//���� Ķ���� ���� �߰�
		@Override
		public void myRaffleCalendarInsert(MyRaffleCalendarDto dto) {
			System.out.println("myRaffleCalendarInsert dao ����");
			sqlSession.insert("myRaffleCalendarInsert", dto);
		}
		
		//���� Ķ���� ���� ����
		@Override
		public void myRaffleCalendarDelete(MyRaffleCalendarDto dto) {
			System.out.println("myRaffleCalendarDelete dao ����");
			sqlSession.delete("myRaffleCalendarDelete", dto);
		}
		

// ������ BOARD----------------------------------------
		/*Board list*/
		@Override
		public ArrayList<BoardDto> blist() {
			ArrayList<BoardDto> dtos = (ArrayList)sqlSession.selectList("blist");		
			return dtos;
		}
		
		/*Board page list */
		@Override
		public ArrayList<BoardDto> bpageList(String pageNo) {
			System.out.println("bpageList");
			int page = Integer.parseInt(pageNo);
			int startNo = (page-1) * 10 +1;
			System.out.println("startNo : " + startNo);
			ArrayList<BoardDto> result = (ArrayList)sqlSession.selectList("bpageList",startNo);
			return result;
		}
		
		//board write */
		@Override
		public void bWrite(String name,String title,String content) {
			BoardDto dto = new BoardDto(name,title,content);
			sqlSession.insert("bWrite",dto);
		}
		
		/*�Խ��� ���� ���� */
		@Override
		public BoardDto bcontentView(String bid) {
			int bId = Integer.parseInt(bid);
			bupHit(bId);		
			BoardDto dto = sqlSession.selectOne("bcontentView",bId);
			return dto;
		}
		
		/*��Ʈ�� �ø��� */
		@Override
		public void bupHit(int bId) {
			sqlSession.update("bupHit",bId);
		}
		
		/*�Խ��� ���� */
		@Override
		public void bmodify(BoardDto dto) {
			sqlSession.update("bmodify",dto);
		}
		
		/*�Խ��� ���� */
		@Override
		public void bdelete(int bId) {
			int res = sqlSession.delete("bdelete",bId);
		}
		
		/*�Խ��� ��� â ���� �ֱ� */
		@Override
		public BoardDto breplyView(int bId) {
			System.out.println("���â �����ֱ�");
			BoardDto dto = sqlSession.selectOne("breplyView",bId);
			return dto;
		}
		
		/*��� ���� ���� */
		@Override
		public void breply(BoardDto dto) {
			System.out.println("��۳��� ���� �ϱ�");
			breplyShape(dto.getbGroup(), dto.getbStep());
			int res = sqlSession.insert("breply",dto);
		}
		
		/*��� ��� ó�� */
		@Override
		public void breplyShape(int bGroup,int bStep) {
			BoardDto dto = new BoardDto(bGroup,bStep);
			int res = sqlSession.update("breplyShape",dto);
		}

}
