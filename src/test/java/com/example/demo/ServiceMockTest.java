package com.example.demo;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.example.demo.form.AttendanceQuery;
import com.example.demo.model.Attendance;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.service.AttendanceService;

@SpringBootTest
public class ServiceMockTest {
	//このテストの目的は、サービス層以外をモック化したテストを行う事。

	@InjectMocks
	private AttendanceService as;

	@Mock
	private Principal principal;

	@Mock
	private AttendanceRepository ar;

	@Mock
	private Attendance attendance;

	@Mock
	private AttendanceQuery aq;

	//空のListの生成
	private List<Attendance> attendances = new ArrayList<Attendance>();

	@Test
	void saveAttendance() {
		as.saveAttendance(attendance);
		verify(ar, times(1)).saveAndFlush(any());
	}

	@Test
	void saveAllAttendance() {
		as.saveAllAttendances(attendances);
		verify(ar, times(1)).saveAll(any());
	}

	@Test
	public void goodByeAttendance() {
		as.goodbyeAttendance(attendance);
		verify(ar, times(1)).deleteById(any());
	}

	@Test
	public void goodByeAttendances() {
		as.goodbyeAttendances(attendances);
		verify(ar, times(1)).deleteAll(any());
	}

	@Test
	public void findAttendanceById() {
		Mockito.when(attendance.getId()).thenReturn((long)1);
		as.findAttendanceById(attendance.getId());
		verify(ar, times(1)).findById(any());
	}

	@Test
	public void getYourAttendance() {
		Mockito.when(principal.getName()).thenReturn("snoopy");
		Mockito.when(ar.findYourAttendance("snoopy", true, null, true, null, true, null, null)).thenReturn(null);
		Mockito.when(aq.getYear()).thenReturn(2020);
		Mockito.when(aq.getMonth()).thenReturn(1);
		Mockito.when(aq.getDay()).thenReturn(1);
		Page<Attendance> attendances = as.getYourAttendance(null, aq, principal, null, null, null);
		verify(ar, times(1)).findYourAttendance("snoopy", false, 2020, false, 1, false, 1,null);
	}


}
