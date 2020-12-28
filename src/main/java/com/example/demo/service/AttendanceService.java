package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.form.AttendanceQuery;
import com.example.demo.model.Attendance;
import com.example.demo.repository.AttendanceRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AttendanceService {
	private final AttendanceRepository attendanceRepository;

	public List<Attendance> doQuery(AttendanceQuery attendanceQueryService) {
		 List<Attendance> attendanceList = null;
		 if(attendanceQueryService.getUsername().length() > 0) {

		attendanceList = attendanceRepository.findByUsernameLikeAndMonthIs("%" + attendanceQueryService.getUsername() + "%", attendanceQueryService.getMonth());
	}
		 return  attendanceList;
	}

	public void saveAttendance(Attendance attendance) {
		attendanceRepository.saveAndFlush(attendance);
	}

	public void goodbyeAttendance(Attendance attendance) {

		attendanceRepository.deleteById(attendance.getId());

	}






}

