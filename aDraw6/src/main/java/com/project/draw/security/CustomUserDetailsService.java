package com.project.draw.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.project.draw.dao.MDao;
import com.project.draw.dto.DrawJoinDto;
import com.project.draw.util.Constant;

public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MDao mdao = Constant.mdao;
		DrawJoinDto dto = mdao.login(username);
		System.out.println("dto " + dto);
		if(dto == null) {
			System.out.println("security���� dto null�� �α��� ����");
			throw new UsernameNotFoundException("No user found with username");
		}
		String pw = dto.getPpw();
		String auth = dto.getAuth();
		System.out.println("pw : " + pw + " auth : " + auth);
		Collection<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
		roles.add(new SimpleGrantedAuthority(auth));
		
		UserDetails user = new User(username, pw , roles);
		
		return user;
	}

}
