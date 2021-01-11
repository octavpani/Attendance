package com.example.demo.service;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.form.AttendanceQuery;
import com.example.demo.model.Attendance;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class AttendanceListService {
	private final AttendanceService attendanceService;


	public Page<Attendance> SelectAttendanceListForAdmin(Pageable pageable, AttendanceQuery attendanceQuery,
			String username, Integer year, Integer month, Integer day) {
		Page<Attendance> attendances = attendanceService.getAttendance(pageable, attendanceQuery, year, month, day, username);
		  return attendances;
	}


	public Page<Attendance> SelectAttendanceListForUser(Pageable pageable, Principal principal, AttendanceQuery attendanceQuery,
			Integer year, Integer month, Integer day) {
		Page<Attendance> attendances = attendanceService.getYourAttendance(pageable, attendanceQuery, principal, year, month, day);
		return attendances;

	}

}
