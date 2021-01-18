package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.UsersCreationDto;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.Role;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
public class HomeController {

	private final SiteUserRepository userRepository;
	private final UserService userService;
	private final BCryptPasswordEncoder passwordEncoder;


	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/")
	public String showList(Authentication loginUser, Model model) {
		model.addAttribute("username", loginUser.getName());
		model.addAttribute("role", loginUser.getAuthorities());
		return "user";
	}

	@GetMapping("/admin/list")
	public String showAdminList(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "list";
	}

/*
	@GetMapping("/register")
	public String register(@ModelAttribute("user") SiteUser user) {
		return "register";
	}

	@PostMapping("/register")
	public String process(@Validated @ModelAttribute("user") SiteUser user, BindingResult result) {
		if (result.hasErrors()) {
			return "register";
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if(user.getUsername().startsWith("Admin_")) {
			user.setRole(Role.ADMIN.name());
		} else {
			user.setRole(Role.USER.name());
		}
		userRepository.save(user);
		return "redirect:/login";
	}
	*/

	@GetMapping("/register")
	public String register(Model model)  {
		 UsersCreationDto usersform = new UsersCreationDto();
		 for (int i = 0; i < 3; i++) {
			 usersform.addSiteUser(new SiteUser());
		 }
		 model.addAttribute("usersCreationDto", usersform);
		 return "register";
	}

	@PostMapping("/register")
	public String process(@Validated @ModelAttribute UsersCreationDto usersCreationDto, BindingResult result, Model model) {

		if (result.hasErrors()) {
			return "register";
		}

		/*if(!UserService.isValidUsers(usersCreationDto.getUsers())) {
			//model.addAttribute("form", form);
			model.addAttribute("error_message", "入力内容に誤りがあります。");
		return "register";
	}
	*/
		for (int i = 0; i < 3; i++) {
			SiteUser user = usersCreationDto.getUsers().get(i);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			if(user.getUsername().startsWith("Admin_")) {
				user.setRole(Role.ADMIN.name());
			} else {
				user.setRole(Role.USER.name());
			}
		}
		userService.saveAll(usersCreationDto.getUsers());

		return "redirect:/login";
	}




}

