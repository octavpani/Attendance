package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Utils;
import com.example.demo.form.SiteUserQuery;
import com.example.demo.model.SiteUser;
import com.example.demo.service.UserService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Controller
public class SiteUserController {
	private final UserService userService;

	@GetMapping("/admin/siteuser/list")
	public ModelAndView showAttendanceList(ModelAndView mv, SiteUserQuery sq,
			@PageableDefault(size = 10)Pageable pageable,
			@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "role", required = false) String role)
	{
		Page<SiteUser> users = userService.getSiteuser(pageable, sq, id, username, role);

		mv.addObject("SiteuserList", users.getContent());
		mv.addObject("users", users);
		mv.addObject("id", id);
		mv.addObject("username", username);
		mv.addObject("role", role);
		mv.addObject("pathWithPage", Utils.pathWithPage("", pageable, "id", id, "username", username, "role", role));
		mv.addObject("pathWithSort", Utils.pathWithSort("", pageable, "id", id, "username", username, "role", role));
		mv.setViewName("siteuserList");
		return mv;
	}

}
