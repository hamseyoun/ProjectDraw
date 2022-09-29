package com.project.draw.dao;

import java.util.ArrayList;

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
import com.project.draw.dto.RaffleUploadDto;
import com.project.draw.dto.Servey1Dto;
import com.project.draw.dto.Servey2Dto;

public interface IDao {
	//�߻�޼���
			public String join(DrawJoinDto dto);
			public DrawJoinDto login(String pid);
			
//������SNS----------------------------------------------------------
		//�̹����Խñ� �ҷ�����
			public ArrayList<IBoardDto> p1item(String iModelName);
			public ArrayList<IBoardDto> iView1();
			public ArrayList<IBoardDto> hotIBoard();
			public ArrayList<MyIBoardDto> p1rUserView();
			public ArrayList<MyIBoardDto> p1sUserView();
		//�Խñ� �󼼺���
			public IBoardDto p2View(int IBoardNum);
			public MyIBoardDto p2PView(String iId);
			public void p2UpHit(int iBoardNum);
		//���������� ����
			public MyIBoardDto p3View(String iId);
			public ArrayList<IBoardDto> p3IList(String iId);
		//������ ����
			public String p3myPModi(MyIBoardDto dto);
			public void p3myModi(MyIBoardDto dto);	
			
		//�̹����Խñ� �ۼ�����
			public String p4WriteI(IBoardDto dto);
			public IBoardDto p4modifyView(int iBoardNum);
			public void p4modify(IBoardDto dto);
			public void p4modifyDelete(int iBoardNum);
		//��۰���
			public void p2mentWrite(int cmIBNum, String cmId, String cmText);
			public ArrayList<CmBoardDto> p2cmView(int cmIBNum);
			public void p2cmDelete(int cmNum);
		//���ƿ����
			public HeartDto p2hCheck(HeartDto hdto);
			public void p2hUp(HeartDto hdto);
			public void p2hDown(int hNum);
			public void p2UpHeart(HeartDto hdto);
			public void p2DownHeart(int hiBoardNum);
			public void p3TotalHUp(String iId);
			public void p3TotalHDown(String iId);
			public ArrayList<IBoardDto> p3IHeartList(String iId);
		//������
			public ArrayList<Servey1Dto> p5View1();
			public ArrayList<Servey2Dto> p5View2();		
//			public void p5Write(Servey1Dto dto);
			

//�̿���SHOP---------------------------------------------------------
			
			public ArrayList<ContentDto> listDao();
			String sell(ContentDto dto);
			public ArrayList<ContentDto> newsort();
			public ArrayList<ContentDto> oldsort();
			public ArrayList<ContentDto> lpricesort();
			public ArrayList<ContentDto> hpricesort();
			void modify(ContentDto dto);
			public ArrayList<ContentDto> apageList(String pageNo);
			public ArrayList<ContentDto> nikeList();
			public ArrayList<ContentDto> adidasList();
			public ArrayList<ContentDto> newbalList();
			public ArrayList<ContentDto> unarmList();
			
//�Լ���LAFFLE-----------------------------------------------------		
			
			public String raffleUpload(RaffleUploadDto rudto);
			public String raffleCarouselUpload(RaffleCarouselImageDto rcidto);
			public ArrayList<RaffleUploadDto> raffleList();
			public void raffleListDelete(int ruId);
			public void addtoCalendar(MyRaffleCalendarDto dto);
			public RaffleUploadDto raffleDetails(String ruCode);
			public ArrayList<RaffleCarouselImageDto> raffleCarouselDetails(String ruCode);
			public void raffleInfoUpload(RaffleInfoUploadDto riudto);
			public ArrayList<RaffleInfoUploadDto> RaffleInfoList(String ruCode);
			public void raffleInfoDelete(int id);
			public void RaffleReplyWrite(String rUserId, String rContent, String rCode);
			public ArrayList<RaffleReplyDto> RaffleReplyList(String rCode);
			public ArrayList<RaffleReplyDto> pagelist(String pageNo, String rCode);
			public void raffleReplyDelete(int rid);
			public ArrayList<MyRaffleCalendarDto> myRaffleCalendarList(String cId);
			public void myRaffleCalendarUpdate(MyRaffleCalendarDto dto);
			public void myRaffleCalendarInsert(MyRaffleCalendarDto dto);
			public void myRaffleCalendarDelete(MyRaffleCalendarDto dto);
			
//������BOARD-----------------------------------------------------	
			
			public ArrayList<BoardDto> blist();
			public ArrayList<BoardDto> bpageList(String pageNo);
			public void bWrite(String name,String title,String content);
			public BoardDto bcontentView(String bid);
			public void bupHit(int bId);
			public void bmodify(BoardDto dto);
			public void bdelete(int bId);
			public BoardDto breplyView(int bId);
			public void breply(BoardDto dto);
			public void breplyShape(int bGroup,int bStep);
}





