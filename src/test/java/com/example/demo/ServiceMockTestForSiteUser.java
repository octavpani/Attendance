package com.example.demo;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.form.SiteUserForm;
import com.example.demo.form.SiteUserQuery;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.service.SiteUserService;

@SpringBootTest
public class ServiceMockTestForSiteUser {
	//このテストの目的は、サービス層以外をモック化する事。

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


	@Test
	void saveSiteUser() {
		sus.save(user);
		verify(sur, times(1)).save(any());
	}

	@Test
	void saveSiteUsers() {
		sur.saveAll(users);
		verify(sur, times(1)).saveAll(any());
	}

	@Test
	void findSiteUserByid() {
		Mockito.when(user.getId()).thenReturn((long)1);
		sus.findSiteUserById(user.getId());
		verify(sur, times(1)).findById(any());
	}




}
