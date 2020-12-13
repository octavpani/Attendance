package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Attendance;
import com.example.demo.model.AttendanceQuery;
import com.example.demo.repository.AttendanceRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AttendanceService {
	private final AttendanceRepository attendanceRepository;

	public List<Attendance> doQuery(AttendanceQuery attendanceQuery) {
		 List<Attendance> attendanceList = null;
		 if(attendanceQuery.getUsername().length() > 0) {

		attendanceList = attendanceRepository.findByUsernameLikeAndMonthIs("%" + attendanceQuery.getUsername() + "%", attendanceQuery.getMonth());
	}
		 return  attendanceList;
	}

}

