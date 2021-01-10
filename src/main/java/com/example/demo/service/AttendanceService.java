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

	public Page<Attendance> getYourAttendanceByYear(Pageable pageable, AttendanceQuery aq, Principal principal) {
		return attendanceRepository.findByYearIsAndUsernameLike(aq.getYear(), principal.getName(),
				pageable);
	}

	public Page<Attendance> getYourAttendanceByYearIsAndDayIs(Pageable pageable, AttendanceQuery aq, Principal principal) {
		return attendanceRepository.findByYearIsAndDayIsAndUsernameLike(aq.getYear(), aq.getDay(), principal.getName(),
				pageable);
	}

	public Page<Attendance> getYourAttendanceByYearIsAndMonthIs(Pageable pageable, AttendanceQuery aq, Principal principal) {
		return attendanceRepository.findByYearIsAndMonthIsAndUsernameLike(aq.getYear(), aq.getMonth(), principal.getName(),
				pageable);
	}

	public Page<Attendance> getYourAttendanceByMonthIsAndDayIs(Pageable pageable, AttendanceQuery aq, Principal principal) {
		return attendanceRepository.findByMonthIsAndDayIsAndUsernameLike(aq.getMonth(), aq.getDay(), principal.getName(),
				pageable);
	}

	public Page<Attendance> getYourAttendanceByYearIsAndMonthIsAndDayIs(Pageable pageable, AttendanceQuery aq, Principal principal) {
		return attendanceRepository.findByYearIsAndMonthIsAndDayIsAndUsernameLike(aq.getYear(), aq.getMonth(), aq.getDay(), principal.getName(),
				pageable);
	}

	public List<Attendance> getYourAllAttendance(Principal principal) {
		return attendanceRepository.findByUsernameLike(principal.getName());
	}



	//以下Adminの検索用
	public Page<Attendance> getAllAttendance(Pageable pageable) {
		return attendanceRepository.findAll(pageable);
	}

	public Page<Attendance> getAttendanceByYearIsAndUsernameLike(Pageable pageable, AttendanceQuery aq) {
		return attendanceRepository.findByYearIsAndUsernameLike(aq.getYear(), aq.getUsername(), pageable);
	}

	public Page<Attendance> getAttendanceByMonthIsAndUsernameLike(Pageable pageable, AttendanceQuery aq) {
		return attendanceRepository.findByMonthIsAndUsernameLike(aq.getMonth(), aq.getUsername(), pageable);
	}


	public Page<Attendance> getAttendanceByDayIsAndUsernameLike(Pageable pageable, AttendanceQuery aq) {
		return attendanceRepository.findByDayIsAndUsernameLike(aq.getDay(), aq.getUsername(), pageable);
	}

	public Page<Attendance> getAttendanceByYearIsAndMonthIs(Pageable pageable, AttendanceQuery aq) {
		return attendanceRepository.findByYearIsAndMonthIs(aq.getYear(), aq.getMonth(), pageable);
	}

	public Page<Attendance> getAttendanceByYearIsAndDayIs(Pageable pageable, AttendanceQuery aq) {
		return attendanceRepository.findByYearIsAndDayIs(aq.getYear(), aq.getDay(), pageable);
	}

	public Page<Attendance> getAttendanceByMonthIsAndDayIs(Pageable pageable, AttendanceQuery aq) {
		return attendanceRepository.findByMonthIsAndDayIs(aq.getMonth(), aq.getDay(), pageable);
	}
	/*
	public Page<Attendance> getAttendanceByUsernameLike(Pageable pageable, AttendanceQuery aq) {
		return  attendanceRepository.findByUsernameLike(aq.getUsername(),  pageable);
	}
	*/
	public Page<Attendance> getAttendanceByYearIs(Pageable pageable, AttendanceQuery aq) {
		return  attendanceRepository.findByYearIs(aq.getYear(),  pageable);
	}

	public Page<Attendance> getAttendanceByDayIs(Pageable pageable, AttendanceQuery aq) {
		return  attendanceRepository.findByDayIs(aq.getDay(),  pageable);
	}

	public Page<Attendance> getAttendanceByMonthIs(Pageable pageable, AttendanceQuery aq) {
		return  attendanceRepository.findByMonthIs(aq.getMonth(),  pageable);
	}

	public Page<Attendance> getAttendanceByYearIsAndMonthIsAndUsernameLike(Pageable pageable, AttendanceQuery aq) {
		return attendanceRepository.findByYearIsAndMonthIsAndUsernameLike(aq.getYear(), aq.getMonth(), aq.getUsername(),
				pageable);
	}

	public Page<Attendance> getAttendanceByYearIsAndDayIsAndUsernameLike(Pageable pageable, AttendanceQuery aq) {
		return attendanceRepository.findByYearIsAndDayIsAndUsernameLike(aq.getYear(), aq.getDay(), aq.getUsername(),
				pageable);
	}

	public Page<Attendance> getAttendanceByMonthIsAndDayIsAndUsernameLike(Pageable pageable, AttendanceQuery aq) {
		return attendanceRepository.findByMonthIsAndDayIsAndUsernameLike(aq.getMonth(), aq.getDay(), aq.getUsername(),
				pageable);
	}

	public Page<Attendance> getAttendanceByYearIsAndMonthIsAndDayIs(Pageable pageable, AttendanceQuery aq) {
		return attendanceRepository.findByYearIsAndMonthIsAndDayIs(aq.getYear(), aq.getMonth(), aq.getDay(),
				pageable);
	}

	public Page<Attendance> getAttendanceByYearIsAndMonthIsAndDayIsAndUsernameLike(Pageable pageable, AttendanceQuery aq) {
		return attendanceRepository.findByYearIsAndMonthIsAndDayIsAndUsernameLike(aq.getYear(), aq.getMonth(), aq.getDay(),
				aq.getUsername(), pageable);
	}

	public Page<Attendance> getAttendance(Pageable pageable, AttendanceQuery aq,
			String username, Integer year, Integer month, Integer day) {
		boolean anyName = aq.getUsername() == null || aq.getUsername().isEmpty();
		boolean anyYear = aq.getYear() == null;
		boolean anyMonth = aq.getMonth() == null;
		boolean anyDay = aq.getDay() == null;
		username = anyName ? "" : ("%" + aq.getUsername() + "%");
		year = anyYear ? null : (aq.getYear());
		month= anyMonth ? null : (aq.getMonth());
		day = anyDay ? null : (aq.getDay());

		return attendanceRepository.find(anyName, aq.getUsername(), anyYear, aq.getYear(),
				anyMonth, aq.getMonth(), anyDay, aq.getDay(),pageable);
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



