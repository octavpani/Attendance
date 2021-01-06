package com.example.demo.service;

import java.security.Principal;
import java.util.List;
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

	//以下userの検索用
	public Page<Attendance> getYourAllAttendance(Principal principal, Pageable pageable) {
		return attendanceRepository.findByUsernameLike(principal.getName(), pageable);
	}

	public Page<Attendance> getYourAttendanceByDay(Pageable pageable, AttendanceQuery aq, Principal principal) {
		return attendanceRepository.findByDayIsAndUsernameLike(aq.getDay(), principal.getName(),
				pageable);
	}
	public Page<Attendance> getYourAttendanceByMonth(Pageable pageable, AttendanceQuery aq, Principal principal) {
		return attendanceRepository.findByMonthIsAndUsernameLike(aq.getMonth(), principal.getName(),
				pageable);
	}

	public Page<Attendance> getYourAttendanceByMonthAndDay(Pageable pageable, AttendanceQuery aq, Principal principal) {
		return attendanceRepository.findByMonthIsAndDayIsAndUsernameLike(aq.getMonth(), aq.getDay(), principal.getName(),
				pageable);
	}

	public List<Attendance> getYourAllAttendance(Principal principal) {
		return attendanceRepository.findByUsernameLike(principal.getName());
	}

	//以下Adminの検索用
	public Page<Attendance> getAllAttendance(Pageable pageable) {
		return attendanceRepository.findAll(pageable);
	}

	public Page<Attendance> getAttendanceByDayIsAndUsernameLike(Pageable pageable, AttendanceQuery aq) {
		return attendanceRepository.findByDayIsAndUsernameLike(aq.getDay(), aq.getUsername(), pageable);
	}

	public Page<Attendance> getAttendanceByMonthIsAndUsernameLike(Pageable pageable, AttendanceQuery aq) {
		return attendanceRepository.findByMonthIsAndUsernameLike(aq.getMonth(), aq.getUsername(), pageable);
	}

	public Page<Attendance> getAttendanceByMonthIsAndDayIs(Pageable pageable, AttendanceQuery aq) {
		return attendanceRepository.findByMonthIsAndDayIs(aq.getMonth(), aq.getDay(), pageable);
	}

	public Page<Attendance> getAttendanceByUsernameLike(Pageable pageable, AttendanceQuery aq) {
		return  attendanceRepository.findByUsernameLike(aq.getUsername(),  pageable);
	}

	public Page<Attendance> getAttendanceByDayIs(Pageable pageable, AttendanceQuery aq) {
		return  attendanceRepository.findByDayIs(aq.getDay(),  pageable);
	}

	public Page<Attendance> getAttendanceByMonthIs(Pageable pageable, AttendanceQuery aq) {
		return  attendanceRepository.findByMonthIs(aq.getMonth(),  pageable);
	}

	public Page<Attendance> getAttendanceByMonthIsAndDayIsAndUsernameLike(Pageable pageable, AttendanceQuery aq) {
		return attendanceRepository.findByMonthIsAndDayIsAndUsernameLike(aq.getMonth(), aq.getDay(), aq.getUsername(),
				pageable);
	}


	//以下投稿編集用
	public void saveAttendance(Attendance attendance) {
		attendanceRepository.saveAndFlush(attendance);
	}

	public void goodbyeAttendance(Attendance attendance) {
		attendanceRepository.deleteById(attendance.getId());
	}

	public Optional<Attendance> findAttendanceById(long id) {
		return attendanceRepository.findById(id);
	}


}



