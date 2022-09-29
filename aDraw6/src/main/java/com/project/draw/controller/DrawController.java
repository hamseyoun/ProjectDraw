package com.project.draw.controller;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.project.draw.command.AddtoCalendarCommand;
import com.project.draw.command.AucPageListCommand;
import com.project.draw.command.AucSellCommand;
import com.project.draw.command.BoardContentCommand;
import com.project.draw.command.BoardDeleteCommand;
import com.project.draw.command.BoardListCommand;
import com.project.draw.command.BoardModifyCommand;
import com.project.draw.command.BoardPageListCommand;
import com.project.draw.command.BoardReplyCommand;
import com.project.draw.command.BoardReplyViewCommand;
import com.project.draw.command.BoardWriteCommand;
import com.project.draw.command.DrawCommand;
import com.project.draw.command.DrawJoinCommand;
import com.project.draw.command.MyRaffleCalendarCommand;
import com.project.draw.command.MyRaffleCalendarDelete;
import com.project.draw.command.MyRaffleCalendarInsert;
import com.project.draw.command.MyRaffleCalendarUpdate;
import com.project.draw.command.P1HotIBoardCommand;
import com.project.draw.command.P1ViewCommand;
import com.project.draw.command.P1itemCommand;
import com.project.draw.command.P1rUserViewCommand;
import com.project.draw.command.P1sUserViewCommand;
import com.project.draw.command.P2MentWriteCommand;
import com.project.draw.command.P2ViewCommand;
import com.project.draw.command.P2cmViewCommand;
import com.project.draw.command.P2hCheckCommand;
import com.project.draw.command.P2hDownCommand;
import com.project.draw.command.P2hUpCommand;
import com.project.draw.command.P2mentDeleteCommand;
import com.project.draw.command.P3IHeartListCommand;
import com.project.draw.command.P3IListCommand;
import com.project.draw.command.P3ViewCommand;
import com.project.draw.command.P3myImodiCommand;
import com.project.draw.command.P3myModiCommand;
import com.project.draw.command.P4WriteCommand;
import com.project.draw.command.P4modifyCommand;
import com.project.draw.command.P4modifyDeleteCommand;
import com.project.draw.command.P4modifyViewCommand;
import com.project.draw.command.P5View1Command;
import com.project.draw.command.P5View2Command;
import com.project.draw.command.RaffleCarouselUploadCommand;
import com.project.draw.command.RaffleDetailsCommand;
import com.project.draw.command.RaffleInfoDeleteCommand;
import com.project.draw.command.RaffleInfoListCommand;
import com.project.draw.command.RaffleInfoUploadCommand;
import com.project.draw.command.RaffleReplyDeleteCommand;
import com.project.draw.command.RaffleReplyListCommand;
import com.project.draw.command.RaffleReplyPageListCommand;
import com.project.draw.command.RaffleReplyWriteCommand;
import com.project.draw.command.RaffleUploadCommand;
import com.project.draw.command.RaffleViewCommand;
import com.project.draw.command.RafflelistDeleteCommand;
import com.project.draw.command.p2ViewProfileCommand;
import com.project.draw.command.priceModifyCommand;
import com.project.draw.dao.MDao;
import com.project.draw.dto.BoardDto;
import com.project.draw.dto.ContentDto;
import com.project.draw.dto.HeartDto;
import com.project.draw.dto.IBoardDto;
import com.project.draw.dto.MyIBoardDto;
import com.project.draw.dto.MyRaffleCalendarDto;
import com.project.draw.dto.RaffleCarouselImageDto;
import com.project.draw.dto.RaffleUploadDto;
import com.project.draw.dto.Servey1Dto;
import com.project.draw.dto.Servey2Dto;
import com.project.draw.util.Constant;

@Controller
public class DrawController {
	
	private DrawCommand com;
	
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		Constant.passwordEncoder = passwordEncoder;
	}
	
	//DrawDaoŬ���� ����
	private MDao mdao;
	@Autowired
	public void setMdao(MDao mdao) {
		this.mdao = mdao;
		Constant.mdao = mdao;
	}
	
	@RequestMapping("/home")
	public String home(HttpServletRequest request, Model model) {
		System.out.println("home");
		ArrayList<ContentDto> list = mdao.listDao();
		model.addAttribute("list",list);
		return "k_home";
	}
	@RequestMapping("/k_mainView")
	public String k_mainView(HttpServletRequest request, Model model) {
		System.out.println("home");
		ArrayList<ContentDto> list = mdao.listDao();
		model.addAttribute("list",list);
		return "k_mainView";
	}
	
	//Ŭ���̾�Ʈ���� ��û�� ajax�� �ǽ�
	@RequestMapping("/joinView") 
	public String joinView() {
		System.out.println("joinview");
		return "k_joinView1";
	}
	
	//joinó��
	@RequestMapping(value= "/join",produces = "application/text; charset=UTF8")
	@ResponseBody
	public String join(HttpServletRequest request,HttpServletResponse response,Model model) {
		System.out.println("join");
		com = new DrawJoinCommand();
		com.execute(request, model);
		String result = (String)request.getAttribute("result");
		System.out.println("join ��Ʈ�ѷ��� result: " + result);
		
		if(result.equals("success")) {
			return "join_success";
		}
		else {
			return "join_failed";
		}
	}
	
	//loginâ ȭ��
	@RequestMapping("/loginView") 
	public String loginView() {
	System.out.println("loginView");
	
	return "k_home";
	}
		
	//�α��� ����
	@RequestMapping("/main")
	public String main(HttpServletRequest request,Model model,Authentication authentication) {
		System.out.println("main");
		getUsername(authentication, request);
		String username = (String)request.getAttribute("username");
		String auth = (String)request.getAttribute("auth");
		ArrayList<ContentDto> list = mdao.listDao();
		model.addAttribute("list",list);
		return "k_mainView";
	}
	//�α��� ���н�
	@RequestMapping("/processLogin")
	public ModelAndView processLogin(
			@RequestParam(value = "log", required = false) String log,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			HttpServletRequest request,HttpSession session,Model pmodel
			) {
		System.out.println("processLogin");
		ModelAndView model = new ModelAndView();	
		if(log != null && log !="") {
			model.addObject("log", "before login!");
		}
		if (error != null && error != "")  { //�α��� ���нÿ� ��ť��Ƽ���� processLogin?error=1
			model.addObject("error", "Invalid username or password!");
		}
		if (logout != null && logout != "") { //processLogin?logout=1
			model.addObject("logout", "You've been logged out successfully.");
		}
		ArrayList<ContentDto> list = mdao.listDao();
		pmodel.addAttribute("list",list);
		model.setViewName("k_home");
		return model;
	}
	
	//�α׾ƿ� ó��
	@RequestMapping("/logoutView") 
	public String logoutView() {
		System.out.println("logoutView");
		return "k_logoutView";
	}	
		
	
//������ SNS =====================================================================
	//Ȩ���� sns�������� �̵�	 (p1����)
	@RequestMapping("/sns")
	public String sns(HttpServletRequest request, Model model) {
		com = new P1ViewCommand();
		com.execute(request, model);
		//��õ���� ��Ʈ������ �Ѹ���
		com = new P1rUserViewCommand();
		com.execute(request, model);
		return "p1"; 
	}
	//��ǰ�˻�â�����̵�
	@RequestMapping("/p1search")
	public String p1search(HttpServletRequest request, Model model) {
		com = new P1sUserViewCommand();
		com.execute(request, model);
		com = new P1ViewCommand();	
		com.execute(request, model);
		return "p1search";
	}
	//��ǰ�����õ� �Ź߰Խù��� ��Ƽ� ������
	@RequestMapping("/p1item")
	public String p1item(HttpServletRequest request, Model model) {
		com = new P1itemCommand();
		com.execute(request, model);
		return "p1item";
	}
	//�ֽŰԽñۺ���
	@RequestMapping("/p1new")
	public String p1new(HttpServletRequest request, Model model) {
		com = new P1ViewCommand();
		com.execute(request, model);
		return "p1new";
	}	
	//�α�Խñ� ����(��Ʈ��)
	@RequestMapping("/p1hot")
	public String p1hot(HttpServletRequest request, Model model) {
		com = new P1HotIBoardCommand();
		com.execute(request, model);
		return "p1hot";
	}
	//p3 ��õ���� �������� �̵�
	@RequestMapping("/p3rUser")
	public String p3rUser(HttpServletRequest request, Model model) {
		com = new P3ViewCommand();
		com.execute(request, model);
		com = new P3IHeartListCommand();
		com.execute(request, model);
		com = new P3IListCommand();
		com.execute(request, model);
		return "p3";
	}
	//p3 �������� �������� �̵�
	@RequestMapping("/p3IList")
	public String p3IList(HttpServletRequest request, Model model) {
		com = new P3ViewCommand();
		com.execute(request, model);
		System.out.println("��Ʈ�ѷ�");
		com = new P3IHeartListCommand();
		com.execute(request, model);
		com = new P3IListCommand();
		com.execute(request, model);
		return "p3";
	}
	//p3 ���������� ����	
	@RequestMapping("/p3my")
	public String p3my(HttpServletRequest request, Model model, Authentication authentication) {
		getUsername(authentication, request);
		String username = (String)request.getAttribute("username");
		String iId = username;
		
		MDao mdao = Constant.mdao;
		MyIBoardDto dto = mdao.p3View(iId);
		model.addAttribute("p3View", dto);
		
		ArrayList<IBoardDto> dtos = mdao.p3IHeartList(iId);
		model.addAttribute("p3IHeartList", dtos);
		
		ArrayList<IBoardDto> p3Idto = mdao.p3IList(iId);
		model.addAttribute("p3IList", p3Idto);
		
		return "p3my";
	}
	//p3my ���� ������ ���� �� p1���� �̵�
	@RequestMapping(value="/p3myPModi", produces = "application/text; charset=UTF8")
	public String p3myPModi(MultipartHttpServletRequest mtpR, Model model) {
		
		String iId = mtpR.getParameter("iId");
		String iProfilePath = null;
		
		MultipartFile mf = mtpR.getFile("iProfilePath");
		//String path = "E:/jje/workSpace/aDraw5/src/main/webapp/resources/images/snsProfile/";
		//String path = "D:/lym/workspace/aDraw5_220830/src/main/webapp/resources/upimage/snsProfile/";				
		String path = "E:/hsy/workspace/aDraw6/src/main/webapp/resources/images/snsProfile/";
		
		String originFileName = mf.getOriginalFilename();
		long prename = System.currentTimeMillis();
		long fileSize = mf.getSize();
		
		String safeFile = path + prename + originFileName;
		iProfilePath = prename + originFileName;
		MyIBoardDto mdto = new MyIBoardDto(iId,iProfilePath);
		
		mtpR.setAttribute("mdto", mdto);
		com = new P3myModiCommand();
		com.execute(mtpR, model);
		
		Map<String, Object> map = model.asMap();
		String res = (String)map.get("result");
		
		if(res.equals("success")) {
			try {
				mf.transferTo(new File(safeFile));
				System.out.println("��Ŭ���������� �������� ����");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return "redirect:sns";
		}
		else {
			return "redirect:sns";
		}
	}
	//p3my ������ �ڱ�Ұ� ����
	@RequestMapping("/p3myModi")
	public String p3myModi(HttpServletRequest request, Model model) {
		com = new P3myImodiCommand();
		com.execute(request, model);
		return "redirect:sns";
	}
	
	//p2 �Խñۻ󼼷� �̵�
	@RequestMapping("/p2")
	public String p2View(HttpServletRequest request, HttpServletResponse response,Model model, Authentication authentication) {
		try {
			String iboardNum = request.getParameter("iBoardNum");
			int hiBoardNum = Integer.parseInt(iboardNum);
			String hId = request.getParameter("hId");
			
			HeartDto hdto = new HeartDto(hiBoardNum,hId);
//					System.out.println(hdto.gethiBoardNum()+ hdto.gethId());
			
			MDao dao = Constant.mdao;
			HeartDto dtos = dao.p2hCheck(hdto);
			
			int result = dtos.gethNum();
			System.out.println("result: " +result);
			
	 		if(result == 0 ) {
				System.out.println("���ƿ�ȵȻ���");
				model.addAttribute("p2h", dtos);
				
				com = new P2ViewCommand();
				com.execute(request, model);
				com = new p2ViewProfileCommand();
				com.execute(request, model);
				return "p2";
			}
			else {
				System.out.println("���ƿ����");
				model.addAttribute("p2h", dtos);
				
				com = new P2ViewCommand();
				com.execute(request, model);
				com = new p2ViewProfileCommand();
				com.execute(request, model);
				return "p2";
			}
		}catch(Exception e) {
			System.out.println("���ƿ�ȵȻ���1");
			
			com = new P2ViewCommand();
			com.execute(request, model);
			com = new p2ViewProfileCommand();
			com.execute(request, model);
			return "p2";
		}
	}
	//���ƿ��ư �ҷ�����
	@RequestMapping("/p2heartBtn")
	public String p2heartBtn() {
		return "p2heartBtn";
	}
	//p2���ƿ�
	@RequestMapping("/p2hUpDown")
	public String p2hUpDown(HeartDto hdto, HttpServletRequest request, HttpServletResponse response,Model model, Authentication authentication) {
		try {
		request.setAttribute("p2hCheck", hdto);
		com = new P2hCheckCommand();
		com.execute(request, model);
		String result = (String)request.getAttribute("result");
		System.out.println("result" + result);
			
	 		if(result == null || result.equals("")) {
				request.setAttribute("p2hUp", hdto);
				System.out.println("���Դϴ�");
				com = new P2hUpCommand();
				com.execute(request, model);
				return "redirect:sns";
			}
			else {
				request.setAttribute("p2hUp", hdto);
				System.out.println("�̹����ƿ䰡�־��");
				com = new P2hDownCommand();
				com.execute(request, model);
				return "redirect:sns";
			}
		}
		catch(Exception e) {
			e.getMessage();
			request.setAttribute("p2hUp", hdto);
			System.out.println("�̹����ƿ䰡�־��1");
			com = new P2hDownCommand();
			com.execute(request, model);
			return "redirect:sns";
		}
	}
	
	//p2��� �ҷ�����
	@RequestMapping("/p2ccmentLoad")
	public String p2ccmentLoad(HttpServletRequest request, Model model) {
		com = new P2ViewCommand();
		com.execute(request, model);
		//�ش�Խñ� ��ۺҷ�����
		com = new P2cmViewCommand();
		com.execute(request, model);
		return "p2ccment";
	}
	//p2��� �ۼ�
	@RequestMapping("/p2mentWrite")
	public String p2mentWrite(HttpServletRequest request, Model model, Authentication authentication) {
		com = new P2MentWriteCommand();
		com.execute(request, model);
		com = new P2ViewCommand();
		com.execute(request, model);
		com = new P2cmViewCommand();
		com.execute(request, model);
		return "p2ccment2";
	}
	//p2��ۻ���
	@RequestMapping("/p2mentDelete")
	public String p2mentDelete(HttpServletRequest request, Model model) {
		System.out.println("p2mentDelete ��û");
		com = new P2mentDeleteCommand();
		com.execute(request, model);
		com = new P2ViewCommand();
		com.execute(request, model);
		com = new p2ViewProfileCommand();
		com.execute(request, model);
		return "p2";
	}	
	
	//p4�Խñ� ���� �� p1�� �̵�
	@RequestMapping("/p4modifyDelete")
	public String p4modifyDelete(HttpServletRequest request, Model model) {
		com = new P4modifyDeleteCommand();
		com.execute(request, model);
			System.out.println("�Խñ��� �����Ǿ����ϴ�");
		com = new P1ViewCommand();
		com.execute(request, model);
		return "p1";
	}
	//p4 �Խñ� �ۼ������� �̵�
	@RequestMapping("/p4WriteIView")
	public String p4WriteIView() {
		return "p4";
	}
	//p4 �Խñ� �ۼ�()
	@RequestMapping(value="/p4WriteI", produces = "application/text; charset=UTF8")
	public String p4WriteI(MultipartHttpServletRequest mtpR, Model model) {
		System.out.println("p4�̹��� �Խñ� �ۼ� ��û");
		
		int iBoardNum = 0;
		String iId = mtpR.getParameter("iId");
		System.out.println("��Ʈ�ѷ�iId:" + iId);
		String iText = mtpR.getParameter("iText");
		String iImagePath = null;
		String iModelName = mtpR.getParameter("iModelName");
		
		MultipartFile mf = mtpR.getFile("iImagePath");
		//String path = "E:/jje/workSpace/aDraw5/src/main/webapp/resources/images/boardImage/";
		//String path = "D:/lym/workspace/aDraw5_220830/src/main/webapp/resources/upimage/boardImage/";				
		String path = "E:/hsy/workspace/aDraw6/src/main/webapp/resources/images/boardImage/";
		
		String originFileName = mf.getOriginalFilename();
		long prename = System.currentTimeMillis();
		
		String safeFile = path + prename + originFileName;
		iImagePath = prename + originFileName;
		System.out.println("iImagePath:" + iImagePath);
		IBoardDto idto = new IBoardDto(iBoardNum,iId,iText,iImagePath,iModelName);
		
		mtpR.setAttribute("idto", idto);
		com = new P4WriteCommand();
		com.execute(mtpR, model);
		System.out.println("Ŀ�ǵ嵿�ۿϷ�");
		
		Map<String, Object> map = model.asMap();
		String res = (String)map.get("result");
		System.out.println("res : " + res);
		
		if(res.equals("success")) {
			try {
				mf.transferTo(new File(safeFile));
				System.out.println("�����ؼ� ������ �����մϴ�");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return "redirect:sns";
		}
		else {
			return "sns";
		}
	}
	//p4M �Խñ� ������������ �̵�
	@RequestMapping("/p4modifyView")
	public String p4modifyView(HttpServletRequest request, Model model) {
		com = new P4modifyViewCommand();
		com.execute(request, model);
		return "p4modifyView";
	}
	//p4M �Խñ� ������ p2�� ������
	@RequestMapping("/p4modify")
		public String p4modify(HttpServletRequest request, Model model) {
			com = new P4modifyCommand();
			com.execute(request, model);
				System.out.println("�Խñ��� �����Ǿ����ϴ�");
			com = new P2ViewCommand();
			com.execute(request, model);
			com = new p2ViewProfileCommand();
			com.execute(request, model);
			return "p2";
		}
	//p4�Խñ� ���� ��ҹ�ư
	@RequestMapping("/p4modifyCancle")
	public String p4modifyCancle(HttpServletRequest request, Model model) {
		System.out.println("�Խñ� ������ ����Ͽ����ϴ�");
		com = new P1ViewCommand();
		com.execute(request, model);
		return "p1";
	}
	
	//p5�������� ���������
	@RequestMapping("/p5")
	public String p5() {
		return "p5Servey";
	}
	@RequestMapping("/bar")
	public String bar() {
		return "p5bar";
	}
	@RequestMapping("/pie")
	public String pie() {
		return "p5pie";
	}
	@RequestMapping("/p5WriteView")
	public String p5WriteView() {
		return "p5WriteView";
	}
	//p5 1�������ͺ��̱�
	@RequestMapping(value= "/p5View1", produces = "application/json; charset=UTF8")
	@ResponseBody
	public JSONObject p5View1(HttpServletRequest request, Model model) {
		//map�������� �� JSON��ü��
		String subcmd = request.getParameter("subcmd");
		
		com = new P5View1Command();
		com.execute(request, model);
		
		JSONObject result = getp5Data1(request,model);
		
		return result;
	}
	//p5 1�������Ͱ�������
	private JSONObject getp5Data1(HttpServletRequest request, Model model) {
		
		HashMap<String,Object> dashMap = (HashMap)model.asMap();
		ArrayList<Servey1Dto> dashList = (ArrayList<Servey1Dto>)dashMap.get("p5View1");
		
		JSONArray dashArr = new JSONArray();
		
		for(Servey1Dto dto:dashList ) {
			JSONObject data = new JSONObject(); //map�������� �� JSON��ü
			data.put("userAge", dto.getUserAge());
			data.put("userM", dto.getUserM());
			data.put("userW", dto.getUserW());
			//key�� jsp���� ���
			
			dashArr.add(data);
		}
		
		JSONObject result = new JSONObject(); //JSONObject�̹Ƿ� map�����̹Ƿ� key/value�� ���� ����
		result.put("datas", dashArr);
		 
		return result;			
		}
		//p5 2�������ͺ��̱�
		@RequestMapping(value= "/p5View2", produces = "application/json; charset=UTF8")
		@ResponseBody
		public JSONObject p5View2(HttpServletRequest request, Model model) {
			//map�������� �� JSON��ü��
			String subcmd = request.getParameter("subcmd");
			
			com = new P5View2Command();
			com.execute(request, model);
			
			JSONObject result = getp5Data2(request,model);
			
			return result;
		}
		//p5 2�������Ͱ�������
		private JSONObject getp5Data2(HttpServletRequest request, Model model) {
			
			HashMap<String,Object> dashMap = (HashMap)model.asMap();
			ArrayList<Servey2Dto> dashList = (ArrayList<Servey2Dto>)dashMap.get("p5View2");
			
			JSONArray dashArr = new JSONArray();
			
			for(Servey2Dto dto:dashList ) {
				JSONObject data = new JSONObject();
				data.put("brandName", dto.getbrandName());
				data.put("bPercent", dto.getbPercent());
				
				dashArr.add(data);
			}
			
			JSONObject result = new JSONObject();
			result.put("datas", dashArr);
			 
			return result;			
		}
		
		
//�̿��� SHOP =================================================================
		
			@RequestMapping("/sell")  //�Ǹŵ��
			public String sell(MultipartHttpServletRequest mphr,Model model) {
				System.out.println("sell");
				String brand = mphr.getParameter("brand");
				String name = mphr.getParameter("name");
				int price =  Integer.parseInt(mphr.getParameter("price"));
				String limit = mphr.getParameter("limit");
				String pdate = mphr.getParameter("pdate");
				String pbuyer = mphr.getParameter("pbuyer");
				int pno = 0; 
				
				String image = null; 
				
				MultipartFile mf = mphr.getFile("image");

				//String path = "E:/jje/workSpace/aDraw5/src/main/webapp/resources/upimage/";
				//String path = "C:/Users/user/Desktop/lymjava/workspace/aDraw6/src/main/webapp/resources/upimage/";
				//String path1 = "C:/Users/user/Desktop/lymjava/apache-tomcat-9.0.63-windows-x64/apache-tomcat-9.0.63/wtpwebapps/aDraw6/resources/upimage/";
				String path = "E:/hsy/workspace/aDraw6/src/main/webapp/resources/upimage/";
				String path1 = "E:/hsy/util/html/apache-tomcat-9.0.63/wtpwebapps/aDraw6/resources/upimage/";
				String originFileName = mf.getOriginalFilename();
				long prename =  System.currentTimeMillis();
				long fileSize = mf.getSize();
				System.out.println("originFileName : " + originFileName);
				System.out.println("fileSize : " + fileSize);
				String safeFile = path + prename + originFileName; 
				String safeFile1 = path1 + prename + originFileName;
				image = prename + originFileName;
				ContentDto pdto = new ContentDto(brand,name,image,price,limit,pdate,pno,pbuyer);
				mphr.setAttribute("pdto", pdto);
				com = new AucSellCommand();
				com.execute(mphr, model);
				
				Map<String, Object> map = model.asMap();
				String res = (String)map.get("result");
				System.out.println("res : " + res);
				if(res.equals("success")) {
					try {
						mf.transferTo(new File(safeFile));
						mf.transferTo(new File(safeFile1));
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					return "redirect:list";
				}
				else {
					return "list";
				}	
			}
			
			@RequestMapping("/modify") //��������� �����ͺ���
			public String modify(MultipartHttpServletRequest mphr,Model model) {
				String name = mphr.getParameter("pnameId");
				String price = mphr.getParameter("priId2");
				String curbuyer = mphr.getParameter("curIdhidden");
				
				System.out.println("modify��û�Դϴ�.");
				com = new priceModifyCommand();
				com.execute(mphr, model);
				
				return "redirect:list";
				
			}
			
			@RequestMapping("/aplist")
			public String apurl(HttpServletRequest request, Model model) {
				System.out.println("aplist��û");
				System.out.println(request.getParameter("pageNo"));
				com = new AucPageListCommand();
				com.execute(request,model);		
				return "listPageList";
			}
			
			@RequestMapping(value={"/list",})
			public String list(HttpServletRequest request, Model model) {
				ArrayList<ContentDto> list = mdao.listDao();
				model.addAttribute("list",list);
				model.addAttribute("sortmainval","����");
				return "list";
			}
			
			@RequestMapping("/nikeView") 
			public String nikeView(HttpServletRequest request, Model model) {
				System.out.println("nikeview");
				ArrayList<ContentDto> list = mdao.nikeList();
				model.addAttribute("list",list);
				model.addAttribute("sortmainval","����");
				return "list";
			}
			
			@RequestMapping("/adidasView") 
			public String adidasView(HttpServletRequest request, Model model) {
				System.out.println("adidasview");
				ArrayList<ContentDto> list = mdao.adidasList();
				model.addAttribute("list",list);
				model.addAttribute("sortmainval","����");
				return "list";
			}
			
			@RequestMapping("/newbalView") 
			public String newbalView(HttpServletRequest request, Model model) {
				System.out.println("newbalview");
				ArrayList<ContentDto> list = mdao.newbalList();
				model.addAttribute("list",list);
				model.addAttribute("sortmainval","����");
				return "list";
			}
			
			@RequestMapping("/underarmView")  
			public String underarmView(HttpServletRequest request, Model model) {
				System.out.println("underarmview");
				ArrayList<ContentDto> list = mdao.unarmList();
				model.addAttribute("list",list);
				model.addAttribute("sortmainval","����");
				return "list";
			}
			/*
			 * 
			 * 
			 * public String newsort(HttpServletRequest request, Model model) {
			 * System.out.println("newsort"); ArrayList<ContentDto> list = dao.newsort();
			 * model.addAttribute("list",list); return "list"; }
			 */
			@RequestMapping("/newsort") //new,old,lprice,hprice ����
			public String newsort(HttpServletRequest request, Model model) {
				System.out.println("newsort");
				ArrayList<ContentDto> list = mdao.newsort();
				model.addAttribute("list",list);
				model.addAttribute("sortmainval","�ֽż�");
				return "list";
			}
			
			@RequestMapping("/oldsort") 
			public String oldsort(HttpServletRequest request, Model model) {
				System.out.println("oldsort");
				ArrayList<ContentDto> list = mdao.oldsort();
				model.addAttribute("list",list);
				model.addAttribute("sortmainval","�����ȼ�");
				return "list";
			}
			
			@RequestMapping("/lpricesort") 
			public String lpricesort(HttpServletRequest request, Model model) {
				System.out.println("lpricesort");
				ArrayList<ContentDto> list = mdao.lpricesort();
				model.addAttribute("list",list);
				model.addAttribute("sortmainval","���ݳ�����");
				return "list";
			}
			
			@RequestMapping("/hpricesort") 
			public String hpricesort(HttpServletRequest request, Model model) {
				System.out.println("hpricesort");
				ArrayList<ContentDto> list = mdao.hpricesort();
				model.addAttribute("list",list);
				model.addAttribute("sortmainval","���ݳ�����");
				return "list";
			}
			
			

		
		

		
//�Լ��� LAFFLE=================================================================	
		

	//���ø���Ʈ ����
	@RequestMapping("/h_upcoming")
	public String raffleView(HttpServletRequest request,Model model) {
		System.out.println("raffleView �޼��� ����");
		com = new RaffleViewCommand();
		com.execute(request, model);
		return "h_upcoming";
	}	
	
	//���� ���ε�
	@RequestMapping("/raffleUpload")
	public String raffleUpload(MultipartHttpServletRequest mphr, Model model) {
		System.out.println("raffleUpload�޼��� ����");
		
		int ruId = 0;
		String ruDate = mphr.getParameter("cDate");
		String ruName = mphr.getParameter("cName");
		String ruNameEng = mphr.getParameter("cNameEng");
		String ruStory = mphr.getParameter("cStory");
		String ruCode = mphr.getParameter("cCode");
		String ruReleaseDate = mphr.getParameter("cReleaseDate");
		String ruPrice = mphr.getParameter("cPrice");
		String ruImage = null;
		String ruOnoffline = mphr.getParameter("cOnoffline");		
		
		MultipartFile mf = mphr.getFile("cImage");
		
		//String path = "C:/Users/LG/Desktop/hsy/workspace/aDraw01/src/main/webapp/resources/upimage/";						
		//String path1 = "C:/Users/LG/Desktop/hsy/util_html/apache-tomcat-9.0.63/wtpwebapps/aDraw01/resources/upimage/";
		//String path = "E:/jje/workSpace/aDraw5/src/main/webapp/resources/upimage/";						
		String path = "E:/hsy/workspace/aDraw6/src/main/webapp/resources/upimage/";
		String path1 = "E:/hsy/util/html/apache-tomcat-9.0.63/wtpwebapps/aDraw6/resources/upimage/";
		
		String originalFileName = mf.getOriginalFilename();
		long prename = System.currentTimeMillis();
		long fileSize = mf.getSize();
		String safeFile = path + prename + originalFileName;
		String safeFile1 = path1 + prename + originalFileName;
		ruImage = prename + originalFileName;
		
	///
		int ruId1 = 0;
		String ruImage1 = null;
		List<MultipartFile> fileList = mphr.getFiles("cImage1");
		System.out.println("cImage1 ���ε� ����");
		for(MultipartFile mf1 : fileList) {
			String originFileName = mf1.getOriginalFilename();
			long prename1 = System.currentTimeMillis();
			long fileSize1 = mf1.getSize();
			String safeFile2 = path + prename1 + originFileName;	
			String safeFile3 = path1 + prename1 + originFileName;
			
			ruImage1 = prename1 + originFileName;
			System.out.println("ruImage1:" + ruImage1);
			
			RaffleCarouselImageDto rcidto = new RaffleCarouselImageDto(ruId1, ruCode, ruImage1);
			mphr.setAttribute("rcidto", rcidto);
			
			com = new RaffleCarouselUploadCommand();
			com.execute(mphr, model);
			
			try {				
				System.out.println("cImage1 ���ε� �Ϸ�");
				mf1.transferTo(new File(safeFile2));
//				mf1.transferTo(new File(safeFile3));
				
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}			
		}
	///
		
		RaffleUploadDto rudto = new RaffleUploadDto(ruId, ruDate, ruName, ruNameEng, ruStory, ruCode, ruReleaseDate, ruPrice, ruImage, ruOnoffline);
		mphr.setAttribute("rudto", rudto);
		
		com = new RaffleUploadCommand();
		com.execute(mphr, model);

		Map<String, Object> map = model.asMap();
		String res = (String) map.get("result");
		if(res.equals("success")) {
			try {
				mf.transferTo(new File(safeFile));
//				mf.transferTo(new File(safeFile1));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			return "redirect:h_upcoming";
		}
		else {
			return "redirect:h_upcoming";
		}
	}	
	
	//���ø���Ʈ �����ϱ�	
	@RequestMapping(value="/raffleListDelete", produces="application/text; charset=UTF8")
	public String raffleListDelete(HttpServletRequest request, Model model) {
		System.out.println("raffleListDelete ��Ʈ�ѷ� ����");
		com = new RafflelistDeleteCommand();
		com.execute(request, model);
		com = new RaffleViewCommand();
		com.execute(request, model);
		return "h_raffleList";
	}
	
	//���� ������ My Raffle Calendar�� ����ϱ� 
	@RequestMapping(value="/addtoCalendar", produces="application/text; charset=UTF8")
	public void addtoCalendar(MyRaffleCalendarDto dto, HttpServletRequest request, Model model) {
		System.out.println("addtoCalendar ��Ʈ�ѷ� ����");
		String cId = request.getParameter("cId");
		String cTitle = request.getParameter("cTitle");
		String cStart = request.getParameter("cStart") + "T00:00:00.000Z";
		String cEnd = request.getParameter("cEnd") + "T00:00:00.000Z";
		String sStart = request.getParameter("sStart");
		String sEnd = request.getParameter("sEnd");
		String cAllDay = "true";

		dto.setcId(cId);
		dto.setcTitle(cTitle);
		dto.setcStart(cStart);
		dto.setcEnd(cEnd);
		dto.setsStart(sStart);
		dto.setsEnd(sEnd);
		dto.setcAllDay(cAllDay);		
		
		request.setAttribute("dto", dto);
		
		com = new AddtoCalendarCommand();
		com.execute(request, model);
	}
	
	//���� ������ ����
	@RequestMapping("/h_raffle")
	public String raffleDetails(HttpServletRequest request, Model model) {
		System.out.println("raffleDetails ��Ʈ�ѷ� ����");
		String ruCode = request.getParameter("ruCode");
		
		com = new RaffleDetailsCommand();
		com.execute(request, model);
		com = new RaffleInfoListCommand();
		com.execute(request, model);
		com = new RaffleReplyListCommand();
		com.execute(request, model);		
		
		if(model.containsAttribute("ruDetails")) {
			System.out.println("success");
			return "h_raffle";
		}
		else {
			return "redirect:h_upcoming";
		}
	}
	
	//���� �������� ���ε� �� ����
	@RequestMapping(value="/raffleInfoUpload", produces="application/text; charset=UTF8")
	public String raffleInfoUpload(HttpServletRequest request, Model model) {
		System.out.println("raffleInfoUpload ��Ʈ�ѷ� ����");
		
		com = new RaffleInfoUploadCommand();
		com.execute(request, model);
		com = new RaffleInfoListCommand();
		com.execute(request, model);
		
		return "h_raffleInfoList";
	}
	//���� �������� �����ϱ�
	@RequestMapping(value="/raffleInfoDelete", produces="application/text; charset=UTF8")
	public String raffleInfoDelete(HttpServletRequest request, Model model) {
		System.out.println("raffleInfoDelete ��Ʈ�ѷ� ����");
		String id = request.getParameter("id");
		String ruCode = request.getParameter("ruCode");
		System.out.println("raffleInfoDelete ��Ʈ�ѷ��� id: " + id + ", ruCode: " +ruCode);
		
		com = new RaffleInfoDeleteCommand();
		com.execute(request, model);
		com = new RaffleInfoListCommand();
		com.execute(request, model);		
		return "h_raffleInfoList";
	}
	
	//���� ����ۼ�
		@RequestMapping(value="/replyWrite", produces="application/text; charset=UTF8")
		public String replyWrite(HttpServletRequest request, Model model) {
			System.out.println("replyWrite ��Ʈ�ѷ��޼��� ����");
			
			String rUserId = request.getParameter("rUserId");
			String rContent = request.getParameter("rContent");
			String ruId = request.getParameter("ruId");
			String ruCode = request.getParameter("ruCode");
			
			System.out.println("replyWrite ��Ʈ�ѷ��޼�����  rUserId: " + rUserId + ", rContent: " + rContent);
			System.out.println("replyWrite ��Ʈ�ѷ��޼�����  ruId: " + ruId + ", ruCode: " + ruCode);
			
			com = new RaffleReplyWriteCommand();
			com.execute(request, model);
			com = new RaffleDetailsCommand();
			com.execute(request, model);
			com = new RaffleReplyListCommand();
			com.execute(request, model);
			
			return "h_raffleReply";
		}
	//���� ��� ���������̼�
	@RequestMapping(value="/plist", produces="application/text; charset=UTF8")
	public String purl(HttpServletRequest request, Model model) {
		System.out.println("plist ��û");
		
		String rCode = request.getParameter("rCode");
		String pageNo = request.getParameter("pageNo");
		System.out.println("plist��Ʈ�ѷ��� rCode: " + rCode);
		System.out.println("plist��Ʈ�ѷ��� pageNo: " + pageNo);
		com = new RaffleReplyPageListCommand();
		com.execute(request, model);
		return "h_rafflePageList";
	}	
	
	//���� ��ۻ���
		@RequestMapping(value="/replyDelete", produces="application/text; charset=UTF8")
		public String replyDelete(HttpServletRequest request, Model model) {
			System.out.println("replyDelete ��Ʈ�ѷ� ����");
			String rId = request.getParameter("rId");
			String rCode = request.getParameter("ruCode");
			String user_id = request.getParameter("user_id");
			String rUserId = request.getParameter("rUserId");
			System.out.println("replyDelete ��Ʈ�ѷ��� rId: " + rId + ", rCode: " + rCode);
			System.out.println("replyDelete ��Ʈ�ѷ��� user_id: " + user_id + ", rUserId: " + rUserId);
		
			com = new RaffleReplyDeleteCommand();
			com.execute(request, model);
			com = new RaffleDetailsCommand();
			com.execute(request, model);
			com = new RaffleReplyListCommand();
			com.execute(request, model);
			
			return "h_raffleReply";		
		}
	
	//���� Ķ���� ������ �̵�
	@RequestMapping("/h_calendar")
	public String h_calendar() {
		System.out.println("h_calendar ��Ʈ�ѷ� ����");
		return "h_calendar";
	}
	
	//���� Ķ���� ����
	@RequestMapping(value="/myRaffleCalendar",produces = "application/json; charset=UTF8")
	@ResponseBody
	public ArrayList<HashMap<String,Object>> myRaffleCalendar(HttpServletRequest request,Model model) {
		System.out.println("myRaffleCalendar ��Ʈ�ѷ� ����");
		System.out.println("cId : " + request.getParameter("cId"));
		
		com = new MyRaffleCalendarCommand();
		com.execute(request, model);
		
		HashMap<String,Object> map = (HashMap)model.asMap();
		ArrayList<MyRaffleCalendarDto> mrcList= (ArrayList)map.get("mrcList");
		
		ArrayList<HashMap<String,Object>> clistArr = new ArrayList<HashMap<String,Object>>();
		//��ȯ�� ArrayList<HashMap<String,Object>>
		
		for(MyRaffleCalendarDto dto : mrcList) {
			HashMap<String,Object> clistMap = new HashMap<String,Object>();
			String cAllDay = dto.getcAllDay();
			boolean allDay;
			if(cAllDay.equals("true")) {
				allDay = true;
			}
			else {
				allDay = false;
			}
			
			clistMap.put("cNo",dto.getcNo());
			clistMap.put("cId",dto.getcId());
			clistMap.put("title",dto.getcTitle());
			clistMap.put("start",dto.getcStart());
			clistMap.put("end",dto.getcEnd());	
			clistMap.put("allDay",allDay);
			
			clistArr.add(clistMap);
		}
		return clistArr;
	}
	
	//���� Ķ���� ���� ����
	@RequestMapping(value="/myRaffleCalendarUpdate",produces = "application/json; charset=UTF8")
	@ResponseBody
	public String myRaffleCalendarUpdate(@RequestBody MyRaffleCalendarDto dto, HttpServletRequest request, Model model) {
		System.out.println("myRaffleCalendarUpdate ��Ʈ�ѷ� ����, cNo: " + dto.getcNo());
		
		request.setAttribute("dto", dto);
		
		com = new MyRaffleCalendarUpdate();
		com.execute(request, model);
		
		return "success";	
	}
	
	//���� Ķ���� ���� �߰�
	@RequestMapping(value="myRaffleCalendarInsert", produces = "application/json; charset=UTF8")
	@ResponseBody
	public String myRaffleCalendarInsert(@RequestBody MyRaffleCalendarDto dto, HttpServletRequest request, Model model) {
		System.out.println("myRaffleCalendarInsert ��Ʈ�ѷ� ����");
		String start = dto.getcStart();
		String end = dto.getcEnd();	
		
		DateTimeFormatter ISO_DATE_TIME = DateTimeFormatter.ISO_DATE_TIME;
		LocalDateTime localDateStart = LocalDateTime.from(ISO_DATE_TIME.parse(start));
		Timestamp stampStart = Timestamp.valueOf(localDateStart);
		LocalDateTime localDateEnd = LocalDateTime.from(ISO_DATE_TIME.parse(end));		
		Timestamp stampEnd = Timestamp.valueOf(localDateEnd);
		
		System.out.println("LocalDateTime start:" + localDateStart);		
		System.out.println("stampStart :" + stampStart);
		System.out.println("stampEnd :" + stampEnd);
		System.out.println("Timestamp string :" + stampEnd.toString());
		System.out.println("LocalDateTime string :" + localDateEnd.toString());
		
		Long posixStart = stampStart.getTime();
		Long posixEnd = stampEnd.getTime();
		
		System.out.println("posixStart :" + posixStart);
		System.out.println("posixEnd :" + posixEnd);
		
		dto.settStart(stampStart);
		dto.settEnd(stampEnd);
		
		request.setAttribute("dto", dto);
		
		com = new MyRaffleCalendarInsert();
		com.execute(request, model);
		
		return "success";
	}

	//���� Ķ���� ���� ����
	@RequestMapping(value="/myRaffleCalendarDelete", produces = "application/json; charset=UTF8")
	@ResponseBody
	public String calendarDelete(@RequestBody MyRaffleCalendarDto dto,HttpServletRequest request,Model model) {
		System.out.println("calendarDelete ��Ʈ�ѷ� ����, cNo: " + dto.getcNo());
		
		request.setAttribute("dto", dto);
		
		com = new MyRaffleCalendarDelete();
		com.execute(request, model);
		
		return "success";
	}
	
	@RequestMapping("/About")
	public String About(HttpServletRequest request, Model model) {
		System.out.println("About ��Ʈ�ѷ� ����");
		return "h_about";
	}
	
	
//������ BOARD=====================================================================		

	//eBoard�κ�
	@RequestMapping("/Board")
	public String Board(HttpServletRequest request, Model model) {
		System.out.println("eBoard��û");
		com = new BoardListCommand();
		com.execute(request,model);
		return "c_BoardViewMain";
	}
	
	@RequestMapping("/board1")
	public String board1(HttpServletRequest request, Model model) {
		System.out.println("eBoard��û");
		com = new BoardListCommand();
		com.execute(request,model);
		return "c_BoardView";
	}
	
	@RequestMapping("/blist")
	public String blist(HttpServletRequest request, Model model) {
		System.out.println("plist��û");
		System.out.println(request.getParameter("pageNo"));
		com = new BoardPageListCommand();
		com.execute(request,model);		
		return "c_pageBoard";
	}
	
	@RequestMapping("/writeView")
	public String writeView() {
		System.out.println("writeView��û");
		return "c_writeView";
	}
	
	
	@RequestMapping(value= "/write",produces = "application/text; charset=UTF8") 
    public String write(HttpServletRequest request,Model model) {
		System.out.println("bWrite��û");
		com = new BoardWriteCommand();
		com.execute(request,model);
		com = new BoardListCommand();
		com.execute(request,model);
		return "c_BoardView";
	}
	
	@RequestMapping("/contentView")
	public String contentView(HttpServletRequest request,Model model) {
		System.out.println("contentView��û�Դϴ�.");
		com = new BoardContentCommand();
		com.execute(request,model);		
		return "c_contentView";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request,Model model) {
		System.out.println("delete��û�Դϴ�.");
		com = new BoardDeleteCommand();
		com.execute(request,model);
		com = new BoardListCommand();
		com.execute(request,model);
		return "c_BoardView";
	}
	
/*
	//Board�κ�
	@RequestMapping("/Board")
	public String eBoard(HttpServletRequest request, Model model) {
		System.out.println("Board��û");
		com = new BoardListCommand();
		com.execute(request,model);
		return "c_BoardView";
	}
	
	@RequestMapping("/bplist")
	public String bpurl(HttpServletRequest request, Model model) {
		System.out.println("plist��û");
		System.out.println(request.getParameter("pageNo"));
		com = new BoardPageListCommand();
		com.execute(request,model);		
		return "c_pageBoard";
	}
	
	@RequestMapping("/bwriteView")
	public String bwriteView() {
		System.out.println("bwriteView��û");
		return "c_writeView";
	}
	
	
	@RequestMapping(value= "/bWrite",produces = "application/text; charset=UTF8") 
    public String bWrite(HttpServletRequest request,Model model) {
		System.out.println("bWrite��û");
		com = new BoardWriteCommand();
		com.execute(request,model);
		com = new BoardListCommand();
		com.execute(request,model);
		return "c_BoardView";
	}
	
	@RequestMapping("/bcontentView")
	public String bcontentView(HttpServletRequest request,Model model) {
		System.out.println("contentView��û�Դϴ�.");
		com = new BoardContentCommand();
		com.execute(request,model);		
		return "c_contentView";
	}
	
	@RequestMapping(value= "/bmodify",produces = "application/text; charset=UTF8") 
	public String bmodify(HttpServletRequest request,Model model) {
		System.out.println("bmodify��û�Դϴ�.");
		com = new BoardModifyCommand();
		com.execute(request, model);
		com = new BoardListCommand();
		com.execute(request, model);
		return "c_BoardView";
	}
	
	@RequestMapping("/bdelete")
	public String bdelete(HttpServletRequest request,Model model) {
		System.out.println("delete��û�Դϴ�.");
		com = new BoardDeleteCommand();
		com.execute(request,model);
		com = new BoardListCommand();
		com.execute(request,model);
		return "c_BoardView";
	}
	
	@RequestMapping("/breplyView")
	public String breplyView(HttpServletRequest request,Model model) {
		System.out.println("breplyView ��û");
		com = new BoardReplyViewCommand();
		com.execute(request, model);
		return "c_replyWrite";
	}
	
	@RequestMapping(value= "/breply",produces = "application/text; charset=UTF8") 
	public String breply(BoardDto dto,HttpServletRequest request,Model model) {
		System.out.println("breply ��û");
		com = new BoardReplyCommand();
		request.setAttribute("formDto", dto);
		com.execute(request,model);
		com = new BoardListCommand();
		com.execute(request,model);
		return "c_BoardView";
	}	
	*/		
		
		
//=====================================================================		
		
		
		
		
		
	//�Ϲ� �޼���
	private void getUsername(Authentication authentication,HttpServletRequest request) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		System.out.println(userDetails.getUsername());//�α����� ���̵�
		Collection<? extends GrantedAuthority>  authorities = authentication.getAuthorities();
		String auth = authorities.toString(); //role�� �� ���ڿ��� ��ȯ
		System.out.println(auth); //[ROLE_USER]����
		request.setAttribute("username", username);
		request.setAttribute("auth", auth);
		
	}
}
