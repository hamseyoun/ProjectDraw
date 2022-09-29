package com.project.draw.command;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.project.draw.dao.MDao;
import com.project.draw.util.Constant;

public class RafflelistDeleteCommand implements DrawCommand {

	@Override
	public void execute(HttpServletRequest request, Model model) {
		System.out.println("RafflelistDeleteCommand Ŀ�ǵ� ����");
		
		MDao ddao = Constant.mdao;
		String ruid = request.getParameter("ruId");
		int ruId = Integer.parseInt(ruid);
		
		System.out.println("RafflelistDeleteCommand Ŀ�ǵ��� ruId: " + ruId);
		
		ddao.raffleListDelete(ruId);

	}

}
