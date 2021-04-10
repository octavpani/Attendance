package com.example.demo.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.exception.FileNotFoundException;
import com.example.demo.form.AttendanceQuery;
import com.example.demo.form.AttendancesDto;
import com.example.demo.form.CsvForm;
import com.example.demo.form.IdListForEdit;
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
		return attendanceRepository.findYourAttendance(principal.getName(), anyYear, aq.getYear(), anyMonth,
				aq.getMonth(), anyDay, aq.getDay(), pageable);
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
		return attendanceRepository.findAttendance(anyName, aq.getUsername(), anyYear, aq.getYear(), anyMonth,
				aq.getMonth(), anyDay, aq.getDay(), pageable);
	}

	//勤怠時間の計算に利用するリスト
	public List<Attendance> getYourAllAttendance(Principal principal) {
		return attendanceRepository.findByUsernameLike(principal.getName());
	}
	//CSVでの出力する際に利用するリスト
	public List<Attendance> getYourAttendance(Principal principal, CsvForm csvForm) {
		return attendanceRepository.findByUsernameLikeAndYearIsAndMonthIs(principal.getName(), csvForm.getYear(),
				csvForm.getMonth());
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

	public void goodbyeAttendances(List<Attendance> attendances) {
		attendanceRepository.deleteAll(attendances);
	}

	public Optional<Attendance> findAttendanceById(Integer id) {
		return attendanceRepository.findById(id);
	}

	public void saveAttendance(@ModelAttribute Attendance attendance, Principal principal) {
		setLoginName(principal, attendance);
		saveAttendance(attendance);
	}

	//不要なリストの要素を削除し、正常なものだけを戻します。
	public AttendancesDto secureIdList(Principal principal, IdListForEdit idListForEdit) {
		List<String> idList = idListForEdit.getIdList();
		AttendancesDto attendancesDto = new AttendancesDto();
		for (String id : idList) {
			attendancesDto.addAttendance(secureAttendanceId(Integer.valueOf(id), principal));
		}
		return attendancesDto;
	}

	//Dtoからフィールドであるusersを取り出します。その際に、usernameを差し込む
	public List<Attendance> setUsernameOnDto(AttendancesDto attendancesDto, Principal principal) {
		List<Attendance> attendances = new ArrayList<Attendance>();
		for (int i = 0; i < attendancesDto.getAttendances().size(); i++) {
			Attendance attendance = attendancesDto.getAttendances().get(i);
			attendance.setUsername(principal.getName());
			attendances.add(attendance);
		}
		return attendances;
	}

	//名前をattendanceに差し込みます
	public void setLoginName(Principal principal, Attendance attendance) {
		attendance.setUsername(principal.getName());
	}

	//idからattendanceを検索します。
	public Attendance secureAttendanceId(Integer id, Principal principal) {
		Optional<Attendance> att = findAttendanceById(id);
		if (!att.isPresent()) {
			throw new FileNotFoundException();
		}
		Attendance attendance = att.get();
		if (!attendance.getUsername().equals(principal.getName())) {
			throw new IllegalArgumentException();
		}
		return attendance;
	}

}
