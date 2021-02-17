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

import com.example.demo.form.SiteUserForm;
import com.example.demo.service.SiteUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {

	private final SiteUserService userService;
	private final BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/")
	public String showTop(Authentication loginUser, Model model) {
		model.addAttribute("username", loginUser.getName());
		model.addAttribute("role", loginUser.getAuthorities());
		return "top";
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("userform", new SiteUserForm());
		return "register";
	}

	@PostMapping("/register")
	public String process(Model model, @Validated @ModelAttribute("userform") SiteUserForm userform, BindingResult result) {
		if (result.hasErrors()) {
			return "register";
		}
		if (!SiteUserService.isValidUser(userform)) {
			model.addAttribute("error_message", "入力内容に誤りがあります。");
			model.addAttribute("mode", null);
			return "register";
		}
		userService.saveSiteUser(userform);
		return "redirect:/login";
	}

}
