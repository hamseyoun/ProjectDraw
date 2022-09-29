package com.project.draw.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.project.draw.dao.MDao;
import com.project.draw.dto.RaffleReplyDto;
import com.project.draw.util.Constant;

public class RaffleReplyListCommand implements DrawCommand {

	@Override
	public void execute(HttpServletRequest request, Model model) {
		System.out.println("RaffleReplyListCommand ����");
		MDao ddao = Constant.mdao;
		
		String rCode = request.getParameter("ruCode");
		System.out.println("RaffleReplyListCommand Ŀ�ǵ��� rCode: " + rCode);
		ArrayList<RaffleReplyDto> rrdtos = ddao.RaffleReplyList(rCode);
		model.addAttribute("rrList", rrdtos);
	}

}
