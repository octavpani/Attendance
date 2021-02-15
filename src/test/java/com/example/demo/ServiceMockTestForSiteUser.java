package com.example.demo;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.form.SiteUserForm;
import com.example.demo.form.SiteUserQuery;
import com.example.demo.form.SiteUsersDto;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.service.SiteUserService;
import com.example.demo.util.Role;

@SpringBootTest
public class ServiceMockTestForSiteUser {
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@InjectMocks
	private SiteUserService sus;

	@Mock
	private SiteUserRepository sur;

	@Mock
	private SiteUserQuery suq;

	@Mock
	private SiteUser user;

	@Mock
	SiteUserForm userform;

	private List<SiteUser> users = new ArrayList<>();
	private SiteUsersDto siteUsersDto = new SiteUsersDto();


	@Test
	void saveSiteUser() {
		sus.save(user);
		verify(sur, times(1)).save(any());
	}

	@Test
	//新規としてのテスト
	void saveSiteUsers() {
		String textFile = "";
        MultipartFile mockFile = new MockMultipartFile("file", textFile.getBytes());
		for (int i = 0;i < 3; i++) {
			siteUsersDto.addSiteUser(new SiteUserForm());
		}
		siteUsersDto.getUsers().get(0).setUsername("test11");
		siteUsersDto.getUsers().get(1).setUsername("test12");
		siteUsersDto.getUsers().get(2).setUsername("test13");
		siteUsersDto.getUsers().get(0).setPassword("test11");
		siteUsersDto.getUsers().get(1).setPassword("test12");
		siteUsersDto.getUsers().get(2).setPassword("test13");

		for (SiteUserForm userform : siteUsersDto.getUsers()) {
			userform.setAvatar(mockFile);

			SiteUser user = new SiteUser();
			user.setUsername(userform.getUsername());
			if (!userform.getPassword().isEmpty()) {
				user.setPassword(passwordEncoder.encode(userform.getPassword()));
			}
			if (userform.getUsername().startsWith("Admin_")) {
				user.setRole(Role.ADMIN.name());
			} else {
				user.setRole(Role.USER.name());
			}
			userform.loadAvaterSrc().ifPresent(user::setAvatar);
			users.add(user);
		}
		assertThat(users.size()).isEqualTo(3);
		assertThat(users.get(0).getUsername()).isEqualTo("test11");
		assertThat(users.get(1).getUsername()).isEqualTo("test12");
		assertThat(users.get(2).getUsername()).isEqualTo("test13");

	}



}
