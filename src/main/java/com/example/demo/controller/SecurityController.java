package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Attendance;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.util.Role;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
public class SecurityController {

	private final SiteUserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final AttendanceRepository AttendanceRepository;


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
	@GetMapping("/register")
	public String register(@ModelAttribute("user") SiteUser user) {
		return "register";
	}
	@PostMapping("/register")
	public String process(@Validated @ModelAttribute("user") SiteUser user, BindingResult result) {
		if(result.hasErrors()) {
			return "register";
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if(user.isAdmin()) {
			user.setRole(Role.ADMIN.name());
		} else {
			user.setRole(Role.USER.name());
		}
		userRepository.save(user);

		return "redirect:/login?register";
	}

	@GetMapping("/attendance/list")
	public ModelAndView showAttendanceList(ModelAndView mv) {
		mv.setViewName("attendanceList");
		List<Attendance> attendanceList = AttendanceRepository.findAll();
		mv.addObject("attendanceList", attendanceList);
		return mv;
	}





	@GetMapping("/attendance")
	public ModelAndView createAttendance1 (ModelAndView mv,
	    @ModelAttribute("attendance") Attendance attendance, Principal principal, Model model) {
	        mv.setViewName("test1");
	        mv.addObject("attendance", attendance);
	        mv.addObject("name", principal.getName());
	        return mv;
	}
	@PostMapping("/attendance")
	public ModelAndView createAttendance (ModelAndView mv,
		    @ModelAttribute("attendance") Attendance attendance, Principal principal) {
		attendance.setUsername(principal.getName());
		AttendanceRepository.saveAndFlush(attendance);
		return showAttendanceList(mv);
	}

	@GetMapping("/edit/{id}")
	public String editAttendance(@PathVariable Long id, Model model) {
		model.addAttribute("attendance", AttendanceRepository.findById(id));
		return "test1";
	}
	@GetMapping("/delete/{id}")
	public String deleteAttendance(@PathVariable Long id) {
		AttendanceRepository.deleteById(id);
		return "redirect:/";
	}



}

