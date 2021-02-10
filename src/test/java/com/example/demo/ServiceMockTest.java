package com.example.demo;

import static org.assertj.core.api.Assertions.*;
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
	@InjectMocks
	private AttendanceService as;
	@Mock
	private Principal principal;
	//@Mock
	//private AttendanceQuery aq;
	@Mock
	private AttendanceRepository ar;

	private Attendance attendance = new Attendance();
	private List<Attendance> attendances = new ArrayList<Attendance>();

	@Test
	void saveAttendance() {
		//Attendance attendance = new Attendance();
		as.saveAttendance(attendance);
		verify(ar, times(1)).saveAndFlush(any());
	}

	@Test
	void saveAllAttendance() {
		//List<Attendance> attendances = new ArrayList<Attendance>();
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
		attendance.setId((long)(1));
		as.findAttendanceById(attendance.getId());
		verify(ar, times(1)).findById(any());
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
