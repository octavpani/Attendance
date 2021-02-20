package com.example.demo;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.example.demo.form.AttendanceQuery;
import com.example.demo.form.AttendancesDto;
import com.example.demo.form.CsvForm;
import com.example.demo.form.IdListForEdit;
import com.example.demo.model.Attendance;
import com.example.demo.service.AttendanceService;

@SpringBootTest
public class ServiceTestsForAttendance {
	//このテストの目的は、Repositoryをモック化せずにテストを行う事。
	//主にモックは、Principalに利用している。
	@InjectMocks
	@Autowired
	private AttendanceService as;

	@Mock
	private Principal principal;

	//空のListの生成
	private List<Attendance> attendances = new ArrayList<Attendance>();
	private AttendanceQuery aq = new AttendanceQuery("", 2021, 4, 1);
	private CsvForm csv = new CsvForm(2021, 4);
	private Page<Attendance> pages;
	private IdListForEdit idListForEdit = new IdListForEdit();
	private AttendancesDto attendancesDto;

	@Test
	public void getYourAttendance0() {
		Mockito.when(principal.getName()).thenReturn("snoopy");
		pages = as.getYourAttendance(null, aq, principal, null, null, null);
		assertThat(pages.getContent().size()).isEqualTo(0);
	}

	@Test
	public void getYourAttendance1() {
		Mockito.when(principal.getName()).thenReturn("admin");
		pages = as.getYourAttendance(null, aq, principal, null, null, null);
		assertThat(pages.getContent().size()).isEqualTo(1);
	}

	@Test
	public void getYourAllAttendance0() {
		Mockito.when(principal.getName()).thenReturn("snoopy");
		attendances = as.getYourAllAttendance(principal);
		assertThat(attendances.size()).isEqualTo(0);
	}

	@Test
	public void getYourAllAttendance1() {
		Mockito.when(principal.getName()).thenReturn("admin");
		attendances = as.getYourAllAttendance(principal);
		assertThat(attendances.size()).isEqualTo(1);
	}

	@Test
	public void getYourAttendanceCsv0() {
		Mockito.when(principal.getName()).thenReturn("snoopy");
		attendances = as.getYourAttendance(principal, csv);
		assertThat(attendances.size()).isEqualTo(0);
	}

	@Test
	public void getYourAttendanceCsv1() {
		Mockito.when(principal.getName()).thenReturn("admin");
		attendances = as.getYourAttendance(principal, csv);
		assertThat(attendances.size()).isEqualTo(1);
	}

	@Test
	public void secureIdList() {
		Mockito.when(principal.getName()).thenReturn("admin");
		idListForEdit.addId(new String());
		idListForEdit.setId(0, "57");
		attendancesDto = as.secureIdList(principal, idListForEdit);
		assertThat(attendancesDto.getAttendances().size()).isEqualTo(1);
	}

	@Test
	public void setUsernameOnDto() {
		Mockito.when(principal.getName()).thenReturn("snoopy");
		AttendancesDto attendancesDto = new AttendancesDto();
		int number = 3;
		for (int i = 0; i < number; i++) {
			attendancesDto.addAttendance(new Attendance());
		}
		attendances = as.setUsernameOnDto(attendancesDto, principal);
		assertThat(attendances.size()).isEqualTo(3);
		assertThat(attendances.get(0).getUsername().equals("snoopy"));
		assertThat(attendances.get(1).getUsername().equals("snoopy"));
		assertThat(attendances.get(2).getUsername().equals("snoopy"));
	}

	@Test
	public void setLoginName() {
		Mockito.when(principal.getName()).thenReturn("snoopy");
		Attendance attendance = new Attendance();
		as.setLoginName(principal, attendance);
		assertThat(attendance.getUsername().equals("snoopy"));
	}

	@Test
	public void secureAttendanceById() {
		Mockito.when(principal.getName()).thenReturn("admin");
		Attendance attendance = as.secureAttendanceId((long) 57, principal);
		assertThat(attendance.getUsername().equals("admin"));
	}

	@Test
	public void expectingNoSuchElement() throws Exception {
		Optional<Attendance> att = as.findAttendanceById((long) 0);
		assertThrows(NoSuchElementException.class, () -> att.get());
	}

	@Test
	public void expectingOptionalError() {
		Optional<Attendance> att = as.findAttendanceById((long) 0);
		if (!att.isPresent()) {
			String ans = "hoge";
			assertThat(ans.equals("hoge"));
		} else {
			fail();
		}
	}

	@Test
	public void expectingNameError() {
		Optional<Attendance> att = as.findAttendanceById((long) 31);
		Attendance attendance = att.get();
		Mockito.when(principal.getName()).thenReturn("snoopy");
		if (!attendance.getUsername().equals(principal.getName())) {
			String ans = "hoge";
			assertThat(ans.equals("hoge"));
		} else {
			fail();
		}
	}
}
