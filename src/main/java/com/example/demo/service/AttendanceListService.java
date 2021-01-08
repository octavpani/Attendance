package com.example.demo.service;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.form.AttendanceQuery;
import com.example.demo.model.Attendance;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class AttendanceListService {
	private final AttendanceService attendanceService;

	public Page<Attendance> SelectAttendanceListForAdmin(Pageable pageable, AttendanceQuery attendanceQuery,
			String username, Integer month, Integer day) {
		Page<Attendance> attendances = null;
		if(day == null && month == null && StringUtils.hasLength(username)) {
			attendances = attendanceService.getAllAttendance(pageable);
		} else if(day == null && month == null) {
			attendances = attendanceService.getAttendanceByUsernameLike(pageable, attendanceQuery);
		} else if(StringUtils.hasLength(username) && month == null) {
			attendances = attendanceService.getAttendanceByDayIs(pageable, attendanceQuery);
		} else if(StringUtils.hasLength(username) && day == null) {
			attendances = attendanceService.getAttendanceByMonthIs(pageable, attendanceQuery);
		} else if (month == null) {
			attendances = attendanceService.getAttendanceByDayIsAndUsernameLike(pageable, attendanceQuery);
		} else if(day == null) {
			attendances = attendanceService.getAttendanceByMonthIsAndUsernameLike(pageable, attendanceQuery);
		} else if(StringUtils.hasLength(username)) {
			attendances = attendanceService.getAttendanceByMonthIsAndDayIs(pageable, attendanceQuery);
		} else {
			attendances = attendanceService.getAttendanceByMonthIsAndDayIsAndUsernameLike(pageable, attendanceQuery);
		}
		return attendances;
	}

	public Page<Attendance> SelectAttendanceListForUser(Pageable pageable, Principal principal, AttendanceQuery attendanceQuery,
			Integer year, Integer month, Integer day) {

		Page<Attendance> attendances = null;
		if(year == null && month == null  && day == null) {
			attendances = attendanceService.getYourAllAttendance(principal, pageable);
		} else if(year == null && month == null) {
			attendances = attendanceService.getYourAttendanceByDay(pageable, attendanceQuery, principal);
		} else if(year == null && day == null) {
			attendances = attendanceService.getYourAttendanceByMonth(pageable, attendanceQuery, principal);
		} else if(month == null && day == null) {
			attendances = attendanceService.getYourAttendanceByYear(pageable, attendanceQuery, principal);
		} else if(year == null) {
			attendances = attendanceService.getYourAttendanceByMonthIsAndDayIs(pageable, attendanceQuery, principal);
		} else if(month == null) {
			attendances = attendanceService.getYourAttendanceByYearIsAndDayIs(pageable, attendanceQuery, principal);
		} else if(day == null){
			attendances = attendanceService.getYourAttendanceByYearIsAndMonthIs(pageable, attendanceQuery, principal);
		} else {
			attendances = attendanceService.getYourAttendanceByYearIsAndMonthIsAndDayIs(pageable, attendanceQuery, principal);
		}
		return attendances;

	}

}
