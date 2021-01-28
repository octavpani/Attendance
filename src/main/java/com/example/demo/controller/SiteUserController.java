package com.example.demo.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Utils;
import com.example.demo.form.IdListForSiteUser;
import com.example.demo.form.SiteUserQuery;
import com.example.demo.form.SiteUsersDto;
import com.example.demo.model.SiteUser;
import com.example.demo.service.UserService;
import com.example.demo.util.Role;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Controller
public class SiteUserController {
	private final UserService userService;
	private final BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/admin/siteuser/list")
	public ModelAndView showAttendanceList(ModelAndView mv, SiteUserQuery sq,
			@PageableDefault(size = 10)Pageable pageable,
			@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "role", required = false) String role)
	{
		//編集のチェックボックス
		IdListForSiteUser idListForSiteUser = new IdListForSiteUser();
		for (int i = 0; i < 10; i++) {
			idListForSiteUser.addId(new String());
		}

		//初期リスト
		Page<SiteUser> users = userService.getSiteuser(pageable, sq, id, username, role);

		mv.addObject("SiteuserList", users.getContent());
		mv.addObject("idListForSiteUser", idListForSiteUser);
		mv.addObject("users", users);
		mv.addObject("id", id);
		mv.addObject("username", username);
		mv.addObject("role", role);
		mv.addObject("pathWithPage", Utils.pathWithPage("", pageable, "id", id, "username", username, "role", role));
		mv.addObject("pathWithSort", Utils.pathWithSort("", pageable, "id", id, "username", username, "role", role));
		mv.setViewName("siteuserList");
		return mv;
	}

	@PostMapping("/admin/siteuser/edit")
		public ModelAndView editSiteUser(ModelAndView mv, IdListForSiteUser idListForSiteUser) {
		List<String> idList = removeVacantList(idListForSiteUser);
		SiteUsersDto siteUsersDto = new SiteUsersDto();
		for (String id : idList) {
			Optional<SiteUser> mayBeUser = userService.findSiteUserById(Long.parseLong(id));
			SiteUser  user = mayBeUser.get();
			siteUsersDto.addSiteUser(user);
		}
		mv.addObject("mode", "update");
		mv.addObject("siteUsersDto", siteUsersDto);
		mv.setViewName("siteUsersForm");
		return mv;
	}

	@PostMapping("/admin/siteuser/update")
	public ModelAndView updateSiteUser(ModelAndView mv, @Validated @ModelAttribute SiteUsersDto siteUsersDto, BindingResult result) {
		if (result.hasErrors()) {
			mv.setViewName("siteUsersForm");
			return mv;
		}
		if(!UserService.isValidUsers(siteUsersDto.getUsers())) {
			mv.addObject("error_message", "入力内容に誤りがあります。");
			mv.setViewName("siteUsersForm");
			return mv;
		}
		for (int i = 0; i < siteUsersDto.getUsers().size(); i++) {
			SiteUser user = siteUsersDto.getUsers().get(i);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			if(user.getUsername().startsWith("Admin_")) {
				user.setRole(Role.ADMIN.name());
			} else {
				user.setRole(Role.USER.name());
			}
		}
		userService.saveAll(siteUsersDto.getUsers());
		mv = new ModelAndView("redirect:/admin/siteuser/list");
		return mv;
	}


	public List<String> removeVacantList(IdListForSiteUser idListForSiteUser) {
		List<String> idList = idListForSiteUser.getIdList();
		Iterator<String> ite = idList.iterator();
		while (ite.hasNext()) {
			String item = ite.next();

			if (item.equals(null)) {
				idList.remove(item);
			}
		}
		return idList;
	}

}
