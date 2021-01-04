package com.example.demo.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.example.demo.exception.FileNotFoundException;
import com.example.demo.form.AttendanceQuery;
import com.example.demo.model.Attendance;
import com.example.demo.service.AttendanceService;
import com.example.demo.service.PracticeCalcService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AttendanceController {

	private final AttendanceService attendanceService;

	@GetMapping("/attendance/list")
	public ModelAndView showAttendanceList(ModelAndView mv, @PageableDefault(size = 5)Pageable pageable, @RequestParam(name = "day", required = false)
	Integer day, @RequestParam(name = "month", required = false) Integer month, Principal principal, AttendanceQuery attendanceQuery) {

		Page<Attendance> attendances = null;
		if(day == null && month == null) {
			attendances = attendanceService.getYourAllAttendance(principal, pageable);
		} else if (month == null){
			attendances = attendanceService.getYourAttendanceByDay(pageable, attendanceQuery, principal);
		} else if(day == null){
			attendances = attendanceService.getYourAttendanceByMonth(pageable, attendanceQuery, principal);
		} else {
			attendances = attendanceService.getYourAttendanceByMonthAndDay(pageable, attendanceQuery, principal);
		}

		mv.addObject("attendanceList", attendances.getContent());
		mv.addObject("attendances", attendances);
		mv.addObject("day", day);
		mv.addObject("pathWithPage", Utils.pathWithPage("", pageable, "day", day, "month", month));
		mv.addObject("pathWithSort", Utils.pathWithSort("", pageable, "day", day, "month", month));
		mv.setViewName("attendanceListForUser");
		return mv;
	}

	//追加したものです
	@GetMapping("/admin/attendance/list")
	public ModelAndView showAdminAttendanceList(ModelAndView mv, @PageableDefault(page = 0, size = 10,sort = "id")Pageable pageable) {
		getAttendanceList(mv, pageable);
		return mv;
	}


	@GetMapping("/attendance")
	public ModelAndView createAttendance(ModelAndView mv, @ModelAttribute Attendance attendance, Principal principal) {
		mv.setViewName("attendanceForm");
		mv.addObject("name", principal.getName());
		return mv;
	}

	@PostMapping("/attendance")
	public ModelAndView createAttendance1(ModelAndView mv, @Validated @ModelAttribute Attendance attendance, Principal principal, BindingResult result) {
		setLoginName(principal, attendance);
		if(result.hasErrors()) {
			mv.setViewName("attendanceForm");
			return mv;
		}
		if (!PracticeCalcService.isValidWorkingRange(
		      attendance.getStaHour(), attendance.getStaMin(),
		      attendance.getEndHour(), attendance.getEndMin())) {
			mv.setViewName("attendanceForm");
			//これは出力された
			mv.addObject("error_message", "入力時刻のエラーです。");
			return mv;
		}
	 	attendanceService.saveAttendance(attendance);
	 	mv =  new ModelAndView("redirect:/attendance/list");
	 		return mv;
		}
		//メソッドにする
		/*if (!PracticeCalcService.isValidWorkingRange(
			      attendance.getStaHour(), attendance.getStaMin(),
			      attendance.getEndHour(), attendance.getEndMin())) {
			  mv.addObject("error_calc", "開始時刻には5以上23以下の数字を入力して下さい。");
			  */



	@GetMapping("/attendance/{id}")
	public ModelAndView getAttendanceById(@PathVariable(name = "id")long id, Principal principal, ModelAndView mv) {
		Attendance attendance = secureAttendanceId(id, principal);
		mv.setViewName("attendanceForm");
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
	public ModelAndView updateAttendance(ModelAndView mv,@Validated @ModelAttribute Attendance attendance, BindingResult result, long id, Principal principal) {
			 setLoginName(principal, attendance);
			 if(result.hasErrors()) {
				 mv.addObject("mode", "update");
			     mv.setViewName("attendanceForm");
			     setLoginName(principal, attendance);
			     mv.addObject("name", principal.getName());
				return mv;
			}
		     if (!PracticeCalcService.isValidWorkingRange(
			      attendance.getStaHour(), attendance.getStaMin(),
			      attendance.getEndHour(), attendance.getEndMin())) {
		    	 //
		    	 mv.addObject("error_message", "入力時刻のエラーです。");
		    	 setLoginName(principal, attendance);
		    	 mv.addObject("name", principal.getName());
		    	 mv.addObject("mode", "update");
		    	 mv.setViewName("attendance_form");
		    	 return mv;
		     }
		     mv =  new ModelAndView("redirect:/attendance/list");
		     attendanceService.saveAttendance(attendance);
		     return mv;
		}

	@PostMapping("/delete")
	public ModelAndView deleteAttendance(ModelAndView mv,long id, Principal principal) {
		Attendance attendance = secureAttendanceId(id, principal);
		attendanceService.goodbyeAttendance(attendance);
		mv =  new ModelAndView("redirect:/attendance/list");
		return mv;
	}



	/*既存のもの　Admin用で使う
	@PostMapping("/attendance/query")
	public ModelAndView queryAttendance(@ModelAttribute AttendanceQuery aq, ModelAndView mv, Pageable pa) {
		//@PageableDefault(page = 0, size = 10)Pageable pageable,
		mv.setViewName("attendanceList");
		Page<Attendance> attendancePage = null;
		if(aq.getDay() == 0) {
			pa = PageRequest.of(0, 10, Sort.by("day").ascending());
		} else {
			pa = PageRequest.of(0, 10, Sort.by("day").descending());
		}
		attendancePage = attendanceService.doQuery(pa, aq);
		mv.addObject("attendanceList", attendancePage.getContent());
		mv.addObject("attendanceQuery", new AttendanceQuery());
		mv.addObject("attendancePage", attendancePage);
		return mv;
		*/

		//並べ替えお試し
		//Pageable pageable = PageRequest.of(0, 10, Sort.by("day").ascending());


	/* 現在ページングへの対応テスト中
	@PostMapping("/attendance/query")
	public ModelAndView queryAttendance(@ModelAttribute AttendanceQuery attendanceQuery, ModelAndView mv) {
		mv.setViewName("attendanceList");
		List<Attendance> attendanceList = null;
		attendanceList = attendanceService.doQuery(attendanceQuery);
		mv.addObject("attendanceList", attendanceList);
		return mv;
	}
	@PostMapping("/attendance/sortbymonth")
	public ModelAndView sortByName(@ModelAttribute AttendanceQuery attendanceQuery, ModelAndView mv) {
		mv.setViewName("attendanceList");
		List<Attendance> attendanceList = attendanceRepository.findAll(Sort.by(Sort.Direction.ASC, "month"));
		mv.addObject("attendanceList", attendanceList);
		return mv;
	}
	*/


/*　前回→全件取得時
	@PostMapping("/attendance/query")
	public ModelAndView queryAttendance(@ModelAttribute AttendanceQuery attendanceQuery, ModelAndView mv) {
		mv.setViewName("attendanceList");
		List<Attendance> attendanceList = null;
		attendanceList = attendanceService.doQuery(attendanceQuery);
		mv.addObject("attendanceList", attendanceList);
		return mv;
	}
*/

	private void setLoginName(Principal principal, Attendance attendance) {
		attendance.setUsername(principal.getName());
	}

	// 以前のもの　Admin用に
	public void getAttendanceList(ModelAndView mv, Pageable pageable) {
		mv.setViewName("attendanceList");
		Page<Attendance> attendancePage = attendanceService.getAllAttendance(pageable);
		mv.addObject("attendanceList", attendancePage.getContent());
		mv.addObject("attendanceQuery", new AttendanceQuery());
		mv.addObject("attendancePage", attendancePage);
	}



		/*　前回→全件取得時のコード
		public void getAttendanceList(ModelAndView mv) {
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

	public Attendance secureAttendanceId(long id, Principal principal) {
		Optional<Attendance> att = attendanceService.findAttendanceById(id);
		Attendance attendance = att.get();
		if (!att.isPresent()) {
			    throw new FileNotFoundException(); // ★
		  }
		if (!attendance.getUsername().equals(principal.getName())) {
			throw new IllegalArgumentException();
		}
		return attendance;
	}


}