package com.example.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
	public ModelAndView showAttendanceList(ModelAndView mv, Pageable pageable) {
		findList(mv, pageable);
		return mv;
	}

	@GetMapping("/attendance")
	public ModelAndView createAttendance(ModelAndView mv, @ModelAttribute("attendance") Attendance attendance,
			Principal principal, Model model) {
		mv.setViewName("test1");
		mv.addObject("name", principal.getName());
		return mv;
	}

	@PostMapping("/attendance")
	public ModelAndView createAttendance(ModelAndView mv, Pageable pageable, @ModelAttribute("attendance") Attendance attendance,
			Principal principal) {
		setLoginName(principal, attendance);
		if (!PracticeCalcService.isValidWorkingRange(
			      attendance.getSta_hour(), attendance.getSta_min(),
			      attendance.getEnd_hour(), attendance.getEnd_min())) {
			//テスト用です。
			  mv.addObject("error_calc", "開始時刻には5以上23以下の数字を入力して下さい。");
			  mv.setViewName("test1");
			  return mv;
			}
		AttendanceRepository.saveAndFlush(attendance);
		return showAttendanceList(mv, pageable);
	}

	@GetMapping("/delete/{id}")
	public String deleteAttendance(@PathVariable(name = "id")long id, Principal principal) {
		Attendance attendance = secureAttendanceId(id, principal);
		AttendanceRepository.deleteById(attendance.getId());
		return "redirect:/";
	}

	@GetMapping("/attendance/{id}")
	public ModelAndView getAttendanceById(@PathVariable(name = "id")long id, Principal principal, ModelAndView mv) {
		Attendance attendance = secureAttendanceId(id, principal);
		mv.setViewName("test1");
		mv.addObject("attendance", attendance);
		mv.addObject("mode", "update");
		return mv;
		}
			/*
			 * 変更案
			 @PreAuthorize （"hasAuthority（ 'ADMIN'）" ）public  String  getMessage （） {
			 return  "Hello Method Security !!" ;
			 }
			 */

	@PostMapping("/attendance/update")
	public String updateAttendance(Attendance attendance, long id, Principal principal) {
		setLoginName(principal, attendance);
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

	//ここから

	public void setLoginName(Principal principal, Attendance attendance) {
		attendance.setUsername(principal.getName());
	}


	public void findList(ModelAndView mv, @PageableDefault(page = 0 , size = 10, sort = "id")Pageable pageable) {
		mv.setViewName("attendanceList");
		Page<Attendance> attendancePage = AttendanceRepository.findAll(pageable);
		mv.addObject("attendanceList", attendancePage.getContent());
		mv.addObject("attendanceQuery", new AttendanceQuery());
		mv.addObject("attendancePage", attendancePage);
	}








		/*　前回→全件取得時のコード
		public void findList(ModelAndView mv) {
		mv.setViewName("attendanceList");
		List<Attendance> attendanceList = AttendanceRepository.findAll();
		int sum_hours = 0;
		int sum_minutes = 0;
		for(int i = 0; i < attendanceList.size(); i++) {
			sum_hours = sum_hours + attendanceList.get(i).workingHours();
			sum_minutes = sum_minutes + attendanceList.get(i).workingMinutes();
		}
		sum_hours += sum_minutes / PracticeCalcService.HOUR;
		sum_minutes = sum_minutes % PracticeCalcService.HOUR;

		mv.addObject("sum_hours", sum_hours);
		mv.addObject("sum_minutes", sum_minutes);

		mv.addObject("attendanceList", attendanceList);
		mv.addObject("attendanceQuery", new AttendanceQuery());
	}
	*/

	public Attendance secureAttendanceId(@PathVariable(name = "id")long id, Principal principal) {
		Optional<Attendance> att = AttendanceRepository.findById(id);
		Attendance attendance = att.get();
		if (!att.isPresent()) {
			    throw new FilenotfoundException(); // ★
		  }
		if (!attendance.getUsername().equals(principal.getName())) {
			throw new IllegalArgumentException();
		}
		return attendance;
	}

	//ここまで

	@ResponseStatus(value = HttpStatus.GONE)
	public class FilenotfoundException
	extends RuntimeException {
	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public class IllegalArgumentException
	extends RuntimeException {
	}
}
