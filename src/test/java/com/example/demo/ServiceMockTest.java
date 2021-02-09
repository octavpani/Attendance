package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import java.security.Principal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.example.demo.form.AttendanceQuery;
import com.example.demo.model.Attendance;
import com.example.demo.service.AttendanceService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ServiceMockTest {
	@InjectMocks
	private AttendanceService as;
	@Mock
	private Principal principal;
	@Mock
	private AttendanceQuery aq;
	@Test
	public void heyMock() {
		Mockito.when(principal.getName()).thenReturn("snoopy");
		Mockito.when(aq.getYear()).thenReturn(null);
		Mockito.when(aq.getMonth()).thenReturn(null);
		Mockito.when(aq.getDay()).thenReturn(null);
		Page<Attendance> attendances = as.getYourAttendance(null, aq, principal, null, null, null);
		assertThat(attendances.getContent().size()).isEqualTo(0);

	}
}
