package com.example.demo;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Attendance;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.service.AttendanceService;

@SpringBootTest
class Attendancetest1ApplicationTests {

	@Autowired
	@InjectMocks
	AttendanceService as;

	@Autowired
	@Mock
	private AttendanceRepository ar;

	@Autowired
	private SiteUserRepository sr;

	/*@Mock
	private Attendance attendance;*/


	@Test
	void contextLoads() {
		assertThat(ar).isNotNull();
	}

	@Test
	void someAttendancesPresent() {
		List<Attendance> at = ar.findAll();
		assertThat(at.size()).isNotEqualTo(3);
	}

	@Test
	void attendanceIsNotNew() {
		List<Attendance> at = ar.findAll();
		assertThat(at.get(0).isNew()).isFalse();
	}


	//AttendanceServiceの動作確認。repositoryの動作確認から。
	/*
	@Test
	void TestOnGetYourAttendances() {
		Page<SiteUser> su = sr.findUser(true, null, true, "", false, "USER", null);
		assertThat(su.getSize()).isNotEqualTo(34);
	}*/
	@Test
	void someUserPresent() {
		List<SiteUser> su = sr.findAll();
		assertThat(su.size()).isNotEqualTo(3);
	}
	@Test
	void siteUserIsNotNew() {
		List<SiteUser> su = sr.findAll();
		assertThat(su.get(0).isNew()).isFalse();
	}

	@Test
	void saveAttendance() {
		Attendance attendance = new Attendance ("test", 2020, 1, 2, 7, 0, 12, 0);
								//spy(new Attendance ("test", 2020, 1, 2, 7, 0, 12, 0));
		as.saveAttendance(attendance);
		verify(ar, times(1)).save(any());

	}

}
