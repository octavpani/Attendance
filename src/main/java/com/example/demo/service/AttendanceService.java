package com.example.demo.service;

import java.security.Principal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.form.AttendanceQuery;
import com.example.demo.model.Attendance;
import com.example.demo.repository.AttendanceRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AttendanceService {
	private final AttendanceRepository attendanceRepository;

	/*
	public List<Attendance> doQuery(AttendanceQuery aq) {
		 List<Attendance> attendanceList = null;
		 if(aq.getUsername().length() > 0 && aq.getMonth() >= 1) {
		attendanceList = attendanceRepository.findByUsernameLikeAndMonthIs("%" + aq.getUsername() + "%", aq.getMonth());
		 }
		 if(aq.getUsername().length() > 0) {
			 attendanceList = attendanceRepository.findByUsernameLike("%" + aq.getUsername() + "%");
		 }

		 if(aq.getMonth() >= 1) {
			 attendanceList = attendanceRepository.findByMonth(aq.getMonth());
		 } else {
			 attendanceList = attendanceRepository.findAll();
		 }
		 return  attendanceList;
	}
	*/
	public Page<Attendance> getYourAllAttendance(Principal principal, Pageable pageable) {
		return attendanceRepository.findByUsernameLike(principal.getName(), pageable);
	}


	public Page<Attendance> getYourAttendanceByDay(Pageable pageable, AttendanceQuery aq, Principal principal) {
		return attendanceRepository.findByDayIsAndUsernameLike(aq.getDay(), pageable,
				principal.getName());
	}
	public Page<Attendance> getYourAttendanceByMonth(Pageable pageable, AttendanceQuery aq, Principal principal) {
		return attendanceRepository.findByMonthIsAndUsernameLike(aq.getMonth(), pageable,
				principal.getName());
	}


	public Page<Attendance> getYourAttendanceByMonthAndDay(Pageable pageable, AttendanceQuery aq, Principal principal) {
		return attendanceRepository.findByMonthIsAndDayIsAndUsernameLike(aq.getMonth(), aq.getDay(), pageable,
				principal.getName());
	}



	/*public Page<Attendance> doQuery(Pageable pageable, AttendanceQuery aq) {
		return attendanceRepository.findByUsernameLikeAndMonthIs("%" + aq.getUsername() + "%", aq.getMonth(), pageable);
	}*/

	public void saveAttendance(Attendance attendance) {
		attendanceRepository.saveAndFlush(attendance);
	}

	public void goodbyeAttendance(Attendance attendance) {

		attendanceRepository.deleteById(attendance.getId());

	}
	public Page<Attendance> getAllAttendance(Pageable pageable) {
		return attendanceRepository.findAll(pageable);
	}


	public Optional<Attendance> findAttendanceById(long id) {
		return attendanceRepository.findById(id);
	}
	/*
	public Page<Attendance> searchAttendanceAsPage(Pageable pageable) {
		return attendanceRepository.findAllByOrderByIdAsc(pageable);
	}
	*/

}



