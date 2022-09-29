package com.project.draw.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.project.draw.dao.MDao;
import com.project.draw.dto.IBoardDto;
import com.project.draw.util.Constant;

public class P1ViewCommand implements DrawCommand {
//p1 �����Խñ� �ҷ�����
	@Override
	public void execute(HttpServletRequest request, Model model) {
		
		MDao mdao = Constant.mdao;
		
		ArrayList<IBoardDto> dtos = mdao.iView1();
		model.addAttribute("iView1", dtos);

	}


}
