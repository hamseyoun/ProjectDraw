package com.project.draw.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.project.draw.dao.MDao;
import com.project.draw.dto.IBoardDto;
import com.project.draw.util.Constant;

public class P1itemCommand implements DrawCommand {

	@Override
	public void execute(HttpServletRequest request, Model model) {
		
		MDao mdao = Constant.mdao;
		
		String iModelName = request.getParameter("iModelName");
		//���⳪�߿� �ѱ� �𵨸��̶�, ������ �ҷ�����
		
		
		ArrayList<IBoardDto> dtos = mdao.p1item(iModelName);
		model.addAttribute("p1item", dtos);
		
		

	}

}
