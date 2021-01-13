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

	public void saveAll(List<SiteUser> users) {
		siteUserRepository.saveAll(users);
	}

}
