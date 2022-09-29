package com.project.draw.command;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.project.draw.dao.MDao;
import com.project.draw.dto.MyRaffleCalendarDto;
import com.project.draw.util.Constant;

public class MyRaffleCalendarInsert implements DrawCommand {

	@Override
	public void execute(HttpServletRequest request, Model model) {
		MDao ddao = Constant.mdao;
		System.out.println("MyRaffleCalendarInsert Ŀ�ǵ� ����");
		MyRaffleCalendarDto dto = (MyRaffleCalendarDto)request.getAttribute("dto");
		ddao.myRaffleCalendarInsert(dto);
	}
}
