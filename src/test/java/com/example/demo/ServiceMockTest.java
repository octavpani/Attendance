package com.example.demo;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Attendance;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.service.AttendanceService;

@SpringBootTest
public class ServiceMockTest {
	@InjectMocks
	//@Autowired
	private AttendanceService as;

	@Mock
	private AttendanceRepository ar;

	@Mock
	private Attendance attendance;

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






}
