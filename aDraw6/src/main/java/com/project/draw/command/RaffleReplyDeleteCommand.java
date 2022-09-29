package com.project.draw.command;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.project.draw.dao.MDao;
import com.project.draw.util.Constant;

public class RaffleReplyDeleteCommand implements DrawCommand {

	@Override
	public void execute(HttpServletRequest request, Model model) {
		
		System.out.println("RaffleReplyDeleteCommand Ŀ�ǵ� ����");
		MDao ddao = Constant.mdao;
		String rId = request.getParameter("rId");
		int rid = Integer.parseInt(rId);
		
		
		
		String user_id = request.getParameter("user_id");
		String rUserId = request.getParameter("rUserId");
		System.out.println("RaffleReplyDeleteCommand Ŀ�ǵ��� user_id: " + user_id + ", rUserId: " + rUserId);
		
		String result = null;
		if(user_id.equals(rUserId)) {			
			ddao.raffleReplyDelete(rid);
			System.out.println("userId�� ��ġ�մϴ�. RaffleReplyDeleteCommand Ŀ�ǵ� ����, rid: " + rid);
			result = "����� �����Ǿ����ϴ�.";
		}
		else {
			System.out.println("userId ����ġ�Դϴ�.");
			result = "������ �����ϴ�.";
		}		
		
		request.setAttribute("result", result);
	}

}
