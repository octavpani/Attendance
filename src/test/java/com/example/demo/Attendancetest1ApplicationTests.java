package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Attendance;
import com.example.demo.repository.AttendanceRepository;

@SpringBootTest
class Attendancetest1ApplicationTests {

	@Autowired
	private AttendanceRepository ar;

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

}
