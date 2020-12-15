package com.example.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Attendance;
import com.example.demo.model.AttendanceQuery;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.service.AttendanceService;
import com.example.demo.util.Role;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Controller
public class SecurityController {

	private final SiteUserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final AttendanceRepository AttendanceRepository;
	private final HttpSession session;
	private final AttendanceService attendanceService;

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
		mv.addObject("attendanceQuery", new AttendanceQuery());
		return mv;
	}





	@GetMapping("/attendance")
	public ModelAndView createAttendance1 (ModelAndView mv,
	    @ModelAttribute("attendance") Attendance attendance, Principal principal, Model model) {
	        mv.setViewName("test1");
	        mv.addObject("attendance", attendance);
	        mv.addObject("name", principal.getName());
	        session.setAttribute("mode", "create");
	        return mv;
	}
	@PostMapping("/attendance")
	public ModelAndView createAttendance (ModelAndView mv,
		    @ModelAttribute("attendance") Attendance attendance, Principal principal) {
		attendance.setUsername(principal.getName());
		AttendanceRepository.saveAndFlush(attendance);
		return showAttendanceList(mv);
	}



	@ResponseStatus(value = HttpStatus.GONE)
	public class FilenotfoundException
	extends RuntimeException {

	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public class IllegalArgumentException
	extends RuntimeException {

	}

	@GetMapping("/delete/{id}")
	public String deleteAttendance(@PathVariable(name = "id")long id, Principal principal) {
		Optional<Attendance> att = AttendanceRepository.findById(id);
		if ( !att.isPresent()) {
		    throw new FilenotfoundException(); // ★
		  }
		Attendance attendance = att.get();

		if(attendance.getUsername() != principal.getName()) {
			throw new IllegalArgumentException();
		}
		AttendanceRepository.deleteById(id);
		return "redirect:/";

	}
	@GetMapping("/attendance/{id}")
	public ModelAndView attendanceById(@PathVariable(name = "id")long id, Principal principal, ModelAndView mv) {
		Optional<Attendance> att = AttendanceRepository.findById(id);
		if ( !att.isPresent()) {
		    throw new FilenotfoundException(); // ★
		  }
		mv.setViewName("test1");
		Attendance attendance = att.get();

		if(attendance.getUsername() != principal.getName()) {
			throw new IllegalArgumentException();
			/*
			 * 変更案
			 @PreAuthorize （"hasAuthority（ 'ADMIN'）" ）
    public  String  getMessage （） {
        return  "Hello Method Security !!" ;
    }

			 */



	} else {
		mv.addObject("attendance", attendance);
		session.setAttribute("mode", "update");
		return mv;

	}


	}

	@PostMapping("/attendance/update")
	public String updateAttendance(@ModelAttribute Attendance attendance, long id, Model model, Principal principal) {
		attendance.setUsername(principal.getName());

			AttendanceRepository.saveAndFlush(attendance);
			return "redirect:/attendance/list";





	}

	@PostMapping("/attendance/query")
	public ModelAndView queryAttendance(@ModelAttribute AttendanceQuery attendanceQuery, ModelAndView mv) {
		mv.setViewName("attendanceList");
		List<Attendance> attendanceList = null;
		attendanceList = attendanceService.doQuery(attendanceQuery);
		mv.addObject("attendanceList", attendanceList);
		return mv;
	}


}

