package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.service.SiteUserService;

@SpringBootTest
public class SiteUserTests {
	//このテストでは、モックを利用せずにテストを行います。

	@Autowired
	SiteUserService us;

	@Autowired
	private SiteUserRepository sr;

	@Test
	void contextLoads() {
		assertThat(sr).isNotNull();
	}

	@Test
	void someSiteUiserPresent() {
		List<SiteUser> users = sr.findAll();
		assertThat(sr).isNotEqualTo(6);
	}

	@Test
	void userIsNotNew() {
		List<SiteUser> users = sr.findAll();
		assertThat(users.get(0).isNew()).isFalse();
	}

	@Test
	void someUserPresent() {
		Page<SiteUser> users = sr.findUser(true, null, false, "Admin_satou", true, null, null);
		assertThat(users.getContent().size()).isEqualTo(1);
	}



}
