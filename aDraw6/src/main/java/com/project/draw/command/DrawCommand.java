package com.project.draw.command;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface DrawCommand {
	//�߻�޼���
		public void execute(HttpServletRequest request,Model model);
}
