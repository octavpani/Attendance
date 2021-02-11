package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import java.security.Principal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.example.demo.form.AttendanceQuery;
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

	@Mock
	private Principal principal;

	@Autowired
	//@Mock
	private AttendanceRepository ar;

	@Autowired
	private SiteUserRepository sr;

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
	//User用の検索メソッドについてのメソッド呼び出し元のRepositoryからテストしている。
	@Test
	void someAttendancePresentForUser() {
		Page<Attendance> attendances = ar.findYourAttendance("Admin_satou", true, null, true, null, true, null, null);
		assertThat(attendances.getContent().size()).isEqualTo(1);
	}
	@Test
	void heIsNotWorkerForUser() {
		Page<Attendance> attendances = ar.findYourAttendance("snoopy", true, null, true, null, true, null, null);
		assertThat(attendances.getContent().size()).isEqualTo(0);
	}
	@Test
	void NoNameSearchForUser() {
		Page<Attendance> attendances = ar.findYourAttendance("", true, null, true, null, true, null, null);
		assertThat(attendances.getContent().size()).isEqualTo(0);
	}
	//Admin用の検索メソッドのテスト。
	@Test
	void someAttendancePresentForAdmin() {
		Page<Attendance> attendances = ar.findAttendance(false, "Admin_satou", true, null, true, null, true, null, null);
		assertThat(attendances.getContent().size()).isEqualTo(1);
	}
	@Test
	void heIsNotWorkerForAdmin() {
		Page<Attendance> attendances = ar.findAttendance(false, "snoopy", true, null, true, null, true, null, null);
		assertThat(attendances.getContent().size()).isEqualTo(0);
	}
	@Test
	void NoNameSearchForAdmin() {
		Page<Attendance> attendances = ar.findAttendance(true, "", true, null, true, null, true, null, null);
		assertThat(attendances.getContent().size()).isEqualTo((65));
	}

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
	public void principalMock() {
		Mockito.when(principal.getName()).thenReturn("snoopy");
		/*Mockito.when(aq.getYear()).thenReturn(null);
		Mockito.when(aq.getMonth()).thenReturn(null);
		Mockito.when(aq.getDay()).thenReturn(null);*/
		AttendanceQuery aq = new AttendanceQuery("", 2020, 1, 1);
		Page<Attendance> attendances = as.getYourAttendance(null, aq, principal, null, null, null);
		assertThat(attendances.getContent().size()).isEqualTo(0);
	}




}
