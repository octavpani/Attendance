package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Utils;
import com.example.demo.form.IdListForSiteUser;
import com.example.demo.form.SiteUserForm;
import com.example.demo.form.SiteUserQuery;
import com.example.demo.form.SiteUsersDto;
import com.example.demo.model.SiteUser;
import com.example.demo.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class SiteUserController {
	private final UserService userService;
	private final BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/admin/siteuser/list")
	public ModelAndView showAttendanceList(ModelAndView mv, SiteUserQuery sq,
			@PageableDefault(size = 10) Pageable pageable,
			@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "role", required = false) String role) {
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

	//単体ユーザーの画像アップロード及び表示のテスト用
	@GetMapping("/admin/siteuser/edit/{id}")
	public ModelAndView editSiteUser(@PathVariable(name = "id") String id, ModelAndView mv) {
		SiteUser user = userService.findSiteUserById(Long.parseLong(id)).get();
		mv.addObject("mode", "update");
		mv.addObject("userform", new SiteUserForm(user));
		mv.setViewName("userForm");
		return mv;
	}
	//テスト用　単体のユーザーを編集している。
	@PostMapping("/admin/siteuser/update/test")
	public ModelAndView updateSiteUser(ModelAndView mv, @ModelAttribute SiteUserForm userform) {
		/* if (result.hasErrors()) {
			mv.setViewName("userForm");
			return mv;		@Validated BindingResult result
		} */
		/*
		if(!UserService.isValidUsers(siteUsersDto.getUsers())) {
			mv.addObject("error_message", "入力内容に誤りがあります。");
			mv.setViewName("siteUsersForm");
			return mv;
		}
		*/
		/*
		SiteUser user = new SiteUser();
		user.setId(userform.getId());
		user.setUsername(userform.getUsername());
		user.setPassword(passwordEncoder.encode(userform.getPassword()));
		if (userform.getUsername().startsWith("Admin_")) {
			user.setRole(Role.ADMIN.name());
		} else {
			user.setRole(Role.USER.name());
		}
		userService.save(user); */
		userService.saveSiteUser(userform);
		mv = new ModelAndView("redirect:/admin/siteuser/list");
		return mv;
	}

	@PostMapping("/admin/siteuser/edit")
	public ModelAndView editSiteUsers(ModelAndView mv, IdListForSiteUser idListForSiteUser) {
		SiteUsersDto siteUsersDto = userService.findUsers(idListForSiteUser);
		mv.addObject("mode", "update");
		mv.addObject("siteUsersDto", siteUsersDto);
		mv.setViewName("siteUsersForm");
		return mv;
	}

	@PostMapping("/admin/siteuser/update")
	public ModelAndView updateSiteUser(ModelAndView mv, @Validated @ModelAttribute SiteUsersDto siteUsersDto,
			BindingResult result) {
		if (result.hasErrors()) {
			mv.addObject("mode", "update");
			mv.setViewName("siteUsersForm");
			return mv;
		}
		if (!UserService.isValidUsers(siteUsersDto.getUsers())) {
			mv.addObject("error_message", "入力内容に誤りがあります。");
			mv.addObject("mode", "update");
			mv.setViewName("siteUsersForm");
			return mv;
		}
		userService.saveSiteUsers(siteUsersDto);
		mv = new ModelAndView("redirect:/admin/siteuser/list");

		//makeSiteUser(mv, siteUsersDto, result);
		return mv;
	}

	@PostMapping("/admin/siteuser/delete")
		public ModelAndView deleteSiteUser(ModelAndView mv, IdListForSiteUser idListForSiteUser) {
			userService.goodByeUsers(idListForSiteUser);
			mv = new ModelAndView("redirect:/admin/siteuser/list");
			return mv;
		}

	@PostMapping("/admin/pre/siteuser/create")
	public ModelAndView prepareSiteUser(ModelAndView mv) {
		mv.setViewName("preSiteUsersForm");
		return mv;
	}

	@GetMapping("/admin/siteuser/create")
	public ModelAndView createSiteUser(ModelAndView mv, @RequestParam(name="number", required=false)Integer number) {
		SiteUsersDto siteUsersDto = new SiteUsersDto();
		for (int i = 0;i < number; i++) {
			siteUsersDto.addSiteUser(new SiteUserForm());
		}
		mv.addObject("siteUsersDto", siteUsersDto);
		mv.addObject("mode", null);
		mv.setViewName("siteUsersForm");
		return mv;
	}
	@PostMapping("/admin/siteuser/create")
	public ModelAndView createSiteUser(ModelAndView mv, @Validated @ModelAttribute SiteUsersDto siteUsersDto,
			BindingResult result) {
		if (result.hasErrors()) {
			mv.setViewName("siteUsersForm");
			mv.addObject("mode", null);
			return mv;
		}
		if (!UserService.isValidUsers(siteUsersDto.getUsers())) {
			mv.addObject("error_message", "入力内容に誤りがあります。");
			mv.setViewName("siteUsersForm");
			mv.addObject("mode", null);
			return mv;
		}
		userService.saveSiteUsers(siteUsersDto);
		mv = new ModelAndView("redirect:/admin/siteuser/list");
		return mv;
	}

	/*public ModelAndView makeSiteUser(ModelAndView mv, @Validated @ModelAttribute SiteUsersDto siteUsersDto,
			BindingResult result) {
		if (result.hasErrors()) {
			mv.setViewName("siteUsersForm");
			return mv;
		}
		if (!UserService.isValidUsers(siteUsersDto.getUsers())) {
			mv.addObject("error_message", "入力内容に誤りがあります。");
			mv.setViewName("siteUsersForm");
			return mv;
		}
		userService.saveSiteUsers(siteUsersDto);
		mv = new ModelAndView("redirect:/admin/siteuser/list");
		return mv;
		*/

}
