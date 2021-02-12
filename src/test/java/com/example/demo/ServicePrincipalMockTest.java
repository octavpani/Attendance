package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.service.AttendanceService;

@SpringBootTest
public class ServicePrincipalMockTest {
	//このテストの目的は、Principalのみモック化して、サービス層をテストする事。
	@InjectMocks
	@Autowired
	private AttendanceService as;

	@Autowired
	private AttendanceRepository ar;

	@Mock
	private Principal principal;

	//空のListの生成
	private List<Attendance> attendances = new ArrayList<Attendance>();
	private AttendanceQuery aq = new AttendanceQuery("", 2022, 8, 20);
	private CsvForm csv = new CsvForm(2022, 8);
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
		Mockito.when(principal.getName()).thenReturn("Admin_satou");
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
		Mockito.when(principal.getName()).thenReturn("Admin_satou");
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
		Mockito.when(principal.getName()).thenReturn("Admin_satou");
		attendances = as.getYourAttendance(principal, csv);
		assertThat(attendances.size()).isEqualTo(1);
	}

	@Test
	public void secureIdList() {
		Mockito.when(principal.getName()).thenReturn("Admin_satou");
		idListForEdit.addId(new String());
		idListForEdit.setId(0, "428");
		attendancesDto = as.secureIdList(principal, idListForEdit);
		assertThat(attendancesDto.getAttendances().size()).isEqualTo(1);
	}


}
