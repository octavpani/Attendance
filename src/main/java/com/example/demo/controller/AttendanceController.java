package com.example.demo.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

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
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.example.demo.Utils;
import com.example.demo.exception.FileNotFoundException;
import com.example.demo.form.AttendanceQuery;
import com.example.demo.form.AttendancesDto;
import com.example.demo.form.IdForEdit;
import com.example.demo.form.IdListForEdit;
import com.example.demo.model.Attendance;
import com.example.demo.service.AttendanceService;
import com.example.demo.service.PracticeCalcService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AttendanceController {

	private final AttendanceService attendanceService;

	@GetMapping("/attendance/list")
	public ModelAndView showAttendanceList(ModelAndView mv, Principal principal, AttendanceQuery attendanceQuery,
			@PageableDefault(size = 10)Pageable pageable,
			@RequestParam(name = "year", required = false) Integer year,
			@RequestParam(name = "month", required = false) Integer month,
			@RequestParam(name = "day", required = false)Integer day)
	{
		//編集のチェックボックス用の配列
		IdListForEdit idListForEdit = new IdListForEdit();
		for (int i = 0; i < 10; i++) {
			idListForEdit.addId(new IdForEdit());
		}

		//初期のリスト表示
		Page<Attendance> attendances = attendanceService.getYourAttendance(pageable, attendanceQuery, principal, year, month, day);

		//勤務時間の計算
		List<Attendance> attendanceList = attendanceService.getYourAllAttendance(principal);
		int sumTime = 0;
		for (int i = 0; i < attendanceList.size(); i++) {
			sumTime = sumTime + attendanceList.get(i).workingHours() * PracticeCalcService.HOUR + attendanceList.get(i).workingMinutes();
		}
		int sumHours = sumTime / PracticeCalcService.HOUR;
		int sumMinutes = sumTime % PracticeCalcService.HOUR;

		mv.addObject("attendanceList", attendances.getContent());
		mv.addObject("idListForEdit", idListForEdit);
		mv.addObject("sumHours", sumHours);
		mv.addObject("sumMinutes", sumMinutes);
		mv.addObject("attendances", attendances);
		mv.addObject("year", year);
		mv.addObject("month", month);
		mv.addObject("day", day);
		mv.addObject("pathWithPage", Utils.pathWithPage("", pageable, "day", day, "month", month, "year", year));
		mv.addObject("pathWithSort", Utils.pathWithSort("", pageable, "day", day, "month", month, "year", year));
		mv.setViewName("attendanceListForUser");
		return mv;
	}


	@GetMapping("/admin/attendance/list")
	public ModelAndView showAttendanceListForAdmin(ModelAndView mv, @PageableDefault(size = 10)Pageable pageable,
			AttendanceQuery attendanceQuery,
			@RequestParam(name="username", required = false)String username,
			@RequestParam(name = "year", required = false)Integer year,
			@RequestParam(name = "month", required = false)Integer month,
			@RequestParam(name = "day", required = false)Integer day)
	{
		Page<Attendance> attendances = attendanceService.getAttendance(pageable, attendanceQuery,
				day, year, month, username);

		mv.addObject("attendanceList", attendances.getContent());
		mv.addObject("attendances", attendances);
		mv.addObject("username", username);
		mv.addObject("year", year);
		mv.addObject("month", month);
		mv.addObject("day", day);
		mv.addObject("pathWithPage", Utils.pathWithPage("", pageable, "year", year, "month", month, "day", day, "username", username));
		mv.addObject("pathWithSort", Utils.pathWithSort("", pageable, "year", year, "month", month, "day", day, "username", username));
		mv.setViewName("attendanceListForAdmin");
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
		if (result.hasErrors()) {
			mv.setViewName("attendanceForm");
			return mv;
		}

		if (!PracticeCalcService.isValidWorkingRange(
		      attendance.getStaHour(), attendance.getStaMin(),
		      attendance.getEndHour(), attendance.getEndMin())) {
			mv.setViewName("attendanceForm");
			mv.addObject("error_message", "入力時刻のエラーです。");
			return mv;
		}

	 	attendanceService.saveAttendance(attendance);
	 	mv =  new ModelAndView("redirect:/attendance/list");
	 		return mv;
		}

	@GetMapping("/form/pre/attendances")
	public ModelAndView setAttendancesForm(ModelAndView mv, Principal principal) {
		mv.setViewName("preAttendancesForm");
		return mv;
	}

	@GetMapping("/form/attendances")
	public ModelAndView createAttendances(ModelAndView mv, @RequestParam(name="year", required = false)Integer year,
			@RequestParam(name="month", required = false)Integer month,
			@RequestParam(name="number", required = false)Integer number) {

		AttendancesDto attendancesDto = new AttendancesDto();
		for (int i = 0; i < number; i++ ) {
			attendancesDto.addAttendance(new Attendance());
		}

		mv.setViewName("attendancesForm");
		mv.addObject("year", year);
		mv.addObject("month", month);
		mv.addObject("number", number);
		mv.addObject("attendancesDto", attendancesDto);
		return mv;
	}

	@PostMapping("/form/attendances")
	public ModelAndView createAttendances(ModelAndView mv, Principal principal, @Validated @ModelAttribute AttendancesDto attendancesDto, BindingResult result) {
		if (result.hasErrors()) {
			mv.setViewName("attendancesForm");
			return mv;
		}

		if (!PracticeCalcService.isValidWorkingRange(attendancesDto.getAttendances())) {
			mv.setViewName("attendancesForm");
			mv.addObject("error_message", "入力時刻のエラーです。");
			return mv;
		}

		for (int i = 0; i < attendancesDto.getAttendances().size(); i++) {
			Attendance attendance = attendancesDto.getAttendances().get(i);
			attendance.setUsername(principal.getName());
		}
		attendanceService.saveAllAttendances(attendancesDto.getAttendances());
		mv =  new ModelAndView("redirect:/attendance/list");
		return mv;
	}

	@PostMapping("/form/attendacnes/edit")
	public ModelAndView editAttendances(ModelAndView mv, Principal principal, IdListForEdit idListForEdit) {

		List<IdForEdit> idList = idListForEdit.getIdList();
		for (int i = idList.size() -1;i > -1; --i) {
			if (idList.get(i).getId() == 0) {
				idList.remove(i);
			}
		}

		AttendancesDto attendancesDto = new AttendancesDto();
		for(int i = 0;i < idList.size(); i++ ) {
			attendancesDto.addAttendance(secureAttendanceId(idList.get(i).getId(), principal));
		}

		mv.addObject("mode", "update");
		mv.addObject("attendancesDto", attendancesDto);
		mv.setViewName("attendancesForm");
		return mv;
	}

	@PostMapping("/attendances/update")
	public ModelAndView updateattendances(ModelAndView mv, Principal principal, @Validated @ModelAttribute AttendancesDto attendancesDto, BindingResult result) {
		if (result.hasErrors()) {
			mv.setViewName("attendancesForm");
			return mv;
		}

		if (!PracticeCalcService.isValidWorkingRange(attendancesDto.getAttendances())) {
			mv.setViewName("attendancesForm");
			mv.addObject("error_message", "入力時刻のエラーです。");
			return mv;
		}

		for (int i = 0; i < attendancesDto.getAttendances().size(); i++) {
			Attendance attendance = attendancesDto.getAttendances().get(i);
			attendance.setUsername(principal.getName());
		}

		attendanceService.saveAllAttendances(attendancesDto.getAttendances());
		mv =  new ModelAndView("redirect:/attendance/list");
		return mv;
	}

	@PostMapping("/attendances/delete")
	public ModelAndView deleteAttendances(ModelAndView mv, Principal principal, IdListForEdit idListForEdit) {
		List<IdForEdit> idList = idListForEdit.getIdList();
		for (int i = idList.size() -1;i > -1; --i) {
			if (idList.get(i).getId() == 0) {
				idList.remove(i);
			}
		}

		AttendancesDto attendancesDto = new AttendancesDto();
		for(int i = 0;i < idList.size(); i++ ) {
			attendancesDto.addAttendance(secureAttendanceId(idList.get(i).getId(), principal));
		}

		attendanceService.goodbyeAttendances(attendancesDto.getAttendances());
		mv =  new ModelAndView("redirect:/attendance/list");
		return mv;
	}

	@GetMapping("/attendance/{id}")
	public ModelAndView getAttendanceById(@PathVariable(name = "id")long id, Principal principal, ModelAndView mv) {
		Attendance attendance = secureAttendanceId(id, principal);
		mv.setViewName("attendanceForm");
		mv.addObject("attendance", attendance);
		mv.addObject("mode", "update");
		return mv;
		}


	@PostMapping("/attendances")
	public ModelAndView getAttendancesById(long id, Principal principal, ModelAndView mv) {
		Attendance attendance = secureAttendanceId(id, principal);
		mv.setViewName("attendanceForm");
		mv.addObject("attendance", attendance);
		mv.addObject("mode", "update");
		return mv;
		}

	@PostMapping("/attendance/update")
	public ModelAndView updateAttendance(ModelAndView mv,@Validated @ModelAttribute Attendance attendance, BindingResult result, long id, Principal principal) {
			 setLoginName(principal, attendance);
			 if (result.hasErrors()) {
				 setLoginName(principal, attendance);
				 mv.addObject("mode", "update");
			     mv.addObject("name", principal.getName());
			     mv.setViewName("attendanceForm");
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

	@GetMapping("/export")
    public void exportToCSV(HttpServletResponse response, Principal principal) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename= " + principal.getName() + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<Attendance> attendances = attendanceService.getYourAllAttendance(principal);
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Attendance ID", "UserName", "Month", "Day", "staHour", "staMin", "endHour", "endMin"};
        String[] nameMapping = {"id", "username", "month", "day", "staHour", "staMin", "endHour", "endMin"};
        csvWriter.writeHeader(csvHeader);
        for (Attendance attendance : attendances) {
            csvWriter.write(attendance, nameMapping);
        }
        csvWriter.close();
    }

	private void setLoginName(Principal principal, Attendance attendance) {
		attendance.setUsername(principal.getName());
	}

	public Attendance secureAttendanceId(long id, Principal principal) {
		Optional<Attendance> att = attendanceService.findAttendanceById(id);
		Attendance attendance = att.get();
		if (!att.isPresent()) {
			    throw new FileNotFoundException();
		  }

		if (!attendance.getUsername().equals(principal.getName())) {
			throw new IllegalArgumentException();
		}

		return attendance;
	}

}