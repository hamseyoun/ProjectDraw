package com.project.draw.command;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.project.draw.dao.MDao;
import com.project.draw.dto.MyRaffleCalendarDto;
import com.project.draw.util.Constant;

public class MyRaffleCalendarDelete implements DrawCommand {

	@Override
	public void execute(HttpServletRequest request, Model model) {
		System.out.println("MyRaffleCalendarDelete Ŀ�ǵ� ����");
		MDao ddao = Constant.mdao;
		
		MyRaffleCalendarDto dto = (MyRaffleCalendarDto)request.getAttribute("dto");

		ddao.myRaffleCalendarDelete(dto);	
	}
}
