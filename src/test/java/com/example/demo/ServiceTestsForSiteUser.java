package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.form.IdListForSiteUser;
import com.example.demo.form.SiteUserForm;
import com.example.demo.form.SiteUsersDto;
import com.example.demo.model.SiteUser;
import com.example.demo.service.SiteUserService;
import com.example.demo.util.Role;

@SpringBootTest
public class ServiceTestsForSiteUser {
	//Repositoryをモック化せずにテストを行う。

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private List<SiteUser> users = new ArrayList<>();
	private SiteUsersDto siteUsersDto = new SiteUsersDto();
	private IdListForSiteUser idListForSiteUser = new IdListForSiteUser();
	@Autowired
	private SiteUserService sus;

	@Test
	//新規作成のテスト
	//次は、分岐の確認→roleをチェックボックスへ。
	//修正時の動作の確認　pwが空の時どうする？
	void processOfSaveSiteUsers() {
		//ダミーのファイルデータを生成
		String textFile = "";
		MultipartFile mockFile = new MockMultipartFile("file", textFile.getBytes());
		for (int i = 0; i < 3; i++) {
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

	@Test
	void findUsers() {
		for (int i = 0; i < 3; i++) {
			idListForSiteUser.addId(new String());
		}
		idListForSiteUser.setId(0, "1");
		idListForSiteUser.setId(1, "2");
		idListForSiteUser.setId(2, "3");
		siteUsersDto = sus.findUsers(idListForSiteUser);

		assertThat(siteUsersDto.getUsers().size()).isEqualTo(3);
		assertThat(siteUsersDto.getUsers().get(0).getUsername()).isEqualTo("admin");
		assertThat(siteUsersDto.getUsers().get(1).getUsername()).isEqualTo("user");
		assertThat(siteUsersDto.getUsers().get(2).getUsername()).isEqualTo("snoopy");
	}

	@Test
	void findUsersForDelete() {
		for (int i = 0; i < 3; i++) {
			idListForSiteUser.addId(new String());
		}
		idListForSiteUser.setId(0, "1");
		idListForSiteUser.setId(1, "2");
		idListForSiteUser.setId(2, "3");
		users = sus.findUsersForDelete(idListForSiteUser);

		assertThat(users.size()).isEqualTo(3);
		assertThat(users.get(0).getUsername()).isEqualTo("admin");
		assertThat(users.get(1).getUsername()).isEqualTo("user");
		assertThat(users.get(2).getUsername()).isEqualTo("snoopy");
	}
}
