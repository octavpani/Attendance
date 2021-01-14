package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.form.SiteUserQuery;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class UserService {
	private final SiteUserRepository siteUserRepository;

	public Page<SiteUser> getSiteuser(Pageable pageable, SiteUserQuery sq,
			Integer id, String username, String role) {
		boolean anyId = sq.getId() == null;
		boolean anyUsername = sq.getUsername() == null || sq.getUsername().isEmpty();
		boolean anyRole = sq.getRole() == null || sq.getRole().isEmpty();

		id = anyId ? null : (sq.getId());
		username = anyUsername ? "" : ("%" +sq.getUsername() + "%");
		role = anyRole ? "" : ("%" + sq.getRole() + "%");


		return siteUserRepository.findUser(anyId, sq.getId(), anyUsername, sq.getUsername(),  anyRole, sq.getRole(), pageable);
	}

	static public boolean isValidUsers(List<SiteUser> users) {
		for(int i = 0; i < users.size();  i++) {
			SiteUser user = users.get(i);
			if(20 <= user.getUsername().length() || user.getUsername().length() <= 2) return false;
			if(255 <= user.getPassword().length() || user.getUsername().length() <= 4) return false;
		}
		return true;

		}

	public void saveAll(List<SiteUser> users) {
		siteUserRepository.saveAll(users);
	}

}
