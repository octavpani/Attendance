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
	 public Page<Attendance> getYourAttendance(Pageable pageable, AttendanceQuery aq, Principal principal,
				Integer year, Integer month, Integer day) {
			boolean anyYear = aq.getYear() == null;
			boolean anyMonth = aq.getMonth() == null;
			boolean anyDay = aq.getDay() == null;
			year = anyYear ? null : (aq.getYear());
			month = anyMonth ? null : (aq.getMonth());
			day = anyDay ? null : (aq.getDay());

			return attendanceRepository.findYourAttendance(principal.getName() , anyYear, aq.getYear(),  anyMonth, aq.getMonth(), anyDay, aq.getDay(), pageable);
		}

	 //勤怠時間
	 public List<Attendance> getYourAllAttendance(Principal principal) {
			return attendanceRepository.findByUsernameLike(principal.getName());
		}

	//以下Adminの検索用
	 public Page<Attendance> getAttendance(Pageable pageable, AttendanceQuery aq,
				Integer year, Integer month, Integer day, String username) {
			boolean anyYear = aq.getYear() == null;
			boolean anyMonth = aq.getMonth() == null;
			boolean anyDay = aq.getDay() == null;
			boolean anyName = aq.getUsername() == null || aq.getUsername().isEmpty();
			year = anyYear ? null : (aq.getYear());
			month = anyMonth ? null : (aq.getMonth());
			day = anyDay ? null : (aq.getDay());
			username = anyName ? "" : ("%" + aq.getUsername() + "%");

			return attendanceRepository.findAttendance(anyName, aq.getUsername(), anyYear, aq.getYear(),  anyMonth, aq.getMonth(), anyDay, aq.getDay(), pageable);
		}


	//以下投稿編集用
	public void saveAttendance(Attendance attendance) {
		attendanceRepository.saveAndFlush(attendance);
	}

	public void saveAllAttendances(List<Attendance> attendances) {
		attendanceRepository.saveAll(attendances);
	}

	public void goodbyeAttendance(Attendance attendance) {
		attendanceRepository.deleteById(attendance.getId());
	}

	public void goodbyeAttendances(Attendance attendance) {
		attendanceRepository.deleteById(attendance.getId());
	}

	public void goodbyeAttendances(List<Attendance> attendances) {
		attendanceRepository.deleteAll(attendances);
	}

	public Optional<Attendance> findAttendanceById(long id) {
		return attendanceRepository.findById(id);
	}


}



