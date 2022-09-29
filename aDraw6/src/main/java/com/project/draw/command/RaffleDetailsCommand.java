package com.project.draw.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.project.draw.dao.MDao;
import com.project.draw.dto.RaffleCarouselImageDto;
import com.project.draw.dto.RaffleUploadDto;
import com.project.draw.util.Constant;

public class RaffleDetailsCommand implements DrawCommand {

	@Override
	public void execute(HttpServletRequest request, Model model) {
		
		System.out.println("RaffleDetailsCommand ����");
		MDao ddao = Constant.mdao;
		
		String ruCode = request.getParameter("ruCode");
		
		System.out.println("���� ruCode: " + ruCode);
		
		RaffleUploadDto dto = ddao.raffleDetails(ruCode);
		ArrayList<RaffleCarouselImageDto> rcidtos = ddao.raffleCarouselDetails(ruCode);
		
		if(dto != null && rcidtos != null) {
			model.addAttribute("ruDetails", dto);
			model.addAttribute("rciDetails", rcidtos);
		}
	}

}
