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
	void someSiteUserPresent() {
		Page<SiteUser> users = sr.findUser(true, null, false, "admin", true, null, null);
		assertThat(users.getContent().size()).isEqualTo(1);
	}

	@Test
	void someAdminPresent() {
		Page<SiteUser> users = sr.findUser(true, null, true, "", false, "ADMIN", null);
		assertThat(users.getContent().size()).isEqualTo(1);
	}

	@Test
	void heIsNotAdmin() {
		Page<SiteUser> users = sr.findUser(true, null, false, "snoopy", false, "ADMIN", null);
		assertThat(users.getContent().size()).isEqualTo(0);
	}

	@Test
	void heIsUser() {
		Page<SiteUser> users = sr.findUser(true, null, false, "snoopy", false, "USER", null);
		assertThat(users.getContent().size()).isEqualTo(1);
	}

	@Test
	void noNameSearch() {
		Page<SiteUser> users = sr.findUser(true, null, true, "", true, null, null);
		assertThat(users.getContent().size()).isEqualTo(3);
	}



}
