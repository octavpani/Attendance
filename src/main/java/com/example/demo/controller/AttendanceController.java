package com.example.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Attendance;
import com.example.demo.model.AttendanceQuery;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.service.AttendanceService;
import com.example.demo.service.PracticeCalcService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AttendanceController {

	private final AttendanceRepository AttendanceRepository;
	private final AttendanceService attendanceService;

	@GetMapping("/attendance/list")
	public ModelAndView showAttendanceList(ModelAndView mv) {
		mv.setViewName("attendanceList");
		List<Attendance> attendanceList = AttendanceRepository.findAll();
		int sum_hours = 0;
		int sum_minutes = 0;
		for(int i = 0; i < attendanceList.size(); i++) {
			sum_hours = sum_hours + attendanceList.get(i).workingHours();
			sum_minutes = sum_minutes + attendanceList.get(i).workingMinutes();
		}
		sum_hours += sum_minutes / 60;
		sum_minutes = sum_minutes % 60;

		mv.addObject("sum_hours", sum_hours);
		mv.addObject("sum_minutes", sum_minutes);
		mv.addObject("attendanceList", attendanceList);
		mv.addObject("attendanceQuery", new AttendanceQuery());
		return mv;
	}

	@GetMapping("/attendance")
	public ModelAndView createAttendance(ModelAndView mv, @ModelAttribute("attendance") Attendance attendance,
			Principal principal, Model model) {
		mv.setViewName("test1");
		mv.addObject("attendance", attendance);
		mv.addObject("name", principal.getName());
	        //session.setAttribute("mode", "create");
		return mv;
	}

	@PostMapping("/attendance")
	public ModelAndView createAttendance(ModelAndView mv, @ModelAttribute("attendance") Attendance attendance,
			Principal principal) {
		attendance.setUsername(principal.getName());
		if (!PracticeCalcService.isValidWorkingRange(
			      attendance.getSta_hour(), attendance.getSta_min(),
			      attendance.getEnd_hour(), attendance.getEnd_min())) {
			  throw new IllegalArgumentException();
			}
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
		if (!att.isPresent()) {
			    throw new FilenotfoundException(); // ★
		  }
		Attendance attendance = att.get();
		if (!attendance.getUsername().equals(principal.getName())) {
			throw new IllegalArgumentException();
		}
		AttendanceRepository.deleteById(id);
		return "redirect:/";
	}

	@GetMapping("/attendance/{id}")
	public ModelAndView attendanceById(@PathVariable(name = "id")long id, Principal principal, ModelAndView mv) {
		Optional<Attendance> att = AttendanceRepository.findById(id);
		if (!att.isPresent()) {
			    throw new FilenotfoundException(); // ★
		  }
		mv.setViewName("test1");
		Attendance attendance = att.get();
		if (!attendance.getUsername().equals(principal.getName())) {
			throw new IllegalArgumentException();

			/*
			 * 変更案
			 @PreAuthorize （"hasAuthority（ 'ADMIN'）" ）public  String  getMessage （） {
			 return  "Hello Method Security !!" ;
			 }

			 */
	  } else {
		      mv.addObject("attendance", attendance);
		      mv.addObject("mode", "update");
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
