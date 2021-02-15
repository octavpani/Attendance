package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.form.IdListForSiteUser;
import com.example.demo.form.SiteUserForm;
import com.example.demo.form.SiteUserQuery;
import com.example.demo.form.SiteUsersDto;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.util.Role;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SiteUserService {
	private final SiteUserRepository siteUserRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	//一覧表示の為の検索メソッド
	public Page<SiteUser> getSiteuser(Pageable pageable, SiteUserQuery sq,
			Integer id, String username, String role) {
		boolean anyId = sq.getId() == null;
		boolean anyUsername = sq.getUsername() == null || sq.getUsername().isEmpty();
		boolean anyRole = sq.getRole() == null || sq.getRole().isEmpty();

		id = anyId ? null : (sq.getId());
		username = anyUsername ? "" : ("%" + sq.getUsername() + "%");
		role = anyRole ? "" : ("%" + sq.getRole() + "%");

		return siteUserRepository.findUser(anyId, sq.getId(), anyUsername, sq.getUsername(), anyRole, sq.getRole(),
				pageable);
	}
	//未テスト。
	static public boolean isValidUsers(List<SiteUserForm> users) {
		for (int i = 0; i < users.size(); i++) {
			SiteUserForm user = users.get(i);
			if (20 <= user.getUsername().length() || user.getUsername().length() <= 2)
				return false;
			if (255 <= user.getPassword().length() || user.getPassword().length() <= 4)
				return false;
		}
		return true;
	}

	public void save(SiteUser user) {
		siteUserRepository.save(user);
	}
	//SiteUsersDto = userformのリストを保持。
	//SiteUserForm = 入力フォーム。画像の変換の為に利用

	public void saveSiteUsers(SiteUsersDto siteUsersDto) {
		List<SiteUser> users = new ArrayList<SiteUser>();
		for (SiteUserForm userform : siteUsersDto.getUsers()) {
			SiteUser user = new SiteUser();
			//既存のidの場合は、idをセット。新規の場合は、idは、null
			if (siteUsersDto.getUsers().get(0).getId() != null) {
				user = findSiteUserById(userform.getId()).get();
			}
			user.setUsername(userform.getUsername());
			//パスワードが入力されていたら、パスワードをハッシュ化
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
		siteUserRepository.saveAll(users);
	}

	public Optional<SiteUser> findSiteUserById(long id) {
		return siteUserRepository.findById(id);
	}

	//idListから、ユーザーを見つける。avatarの関係上、フォームクラスに詰めなおしている。
	public SiteUsersDto findUsers(IdListForSiteUser idListForSiteUser) {
		List<String> idList = idListForSiteUser.getIdList();
		SiteUsersDto siteUsersDto = new SiteUsersDto();
		for (String id : idList) {
			Optional<SiteUser> mayBeUser = findSiteUserById(Long.parseLong(id));
			SiteUser user = mayBeUser.get();
			SiteUserForm userform = new SiteUserForm(user);
			if (userform.getAvatarSrc() == null) {
				userform.setAvatarSrc("");
			}
			siteUsersDto.addSiteUser(userform);
		}
		return siteUsersDto;
	}

	//idListからユーザーを見つける。戻り値は、siteuserのリスト
	public List<SiteUser> findUsersForDelete(IdListForSiteUser idListForSiteUser) {
		List<String> idList = idListForSiteUser.getIdList();
		List<SiteUser> users = new ArrayList<SiteUser>();
		for (String id : idList) {
			Optional<SiteUser> mayBeUser = findSiteUserById(Long.parseLong(id));
			SiteUser user = mayBeUser.get();
			users.add(user);
		}
		return users;
	}

	//ユーザーの一括削除
	public void goodByeUsers(IdListForSiteUser idListForSiteUser) {
		List<SiteUser> users = findUsersForDelete(idListForSiteUser);
		siteUserRepository.deleteAll(users);
	}

}
