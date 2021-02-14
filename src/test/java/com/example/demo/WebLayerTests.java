package com.example.demo;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.SiteUserController;
import com.example.demo.model.CustomUserDetails;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.service.AttendanceService;
import com.example.demo.service.PracticeCalcService;
import com.example.demo.service.SiteUserService;

@WebMvcTest(controllers = SiteUserController.class)
public class WebLayerTests {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	SiteUserRepository siteUserRepository;

	@MockBean
	AttendanceRepository attendanceRepository;

	@MockBean
	private BCryptPasswordEncoder pwEncoder;

	@MockBean
	AttendanceService attendanceService;

	@MockBean
	PracticeCalcService practiceCalcService;

	@MockBean
	UserDetailsService userDetailsService;

	@MockBean
	SiteUserService userService;

	@Test
	public void indexOfSiteUserListShouldBeRedirected() throws Exception {
		mockMvc.perform(get("/admin/siteuser/list"))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("http://localhost/login"));

	}

	@Test
	@WithMockUser(username = "user", authorities = "USER")
	public void shouldBeAdmin() throws Exception {
		mockMvc.perform(get("/admin/siteuser/list"))
				.andExpect(status().isForbidden());
	}

	@Test
	public void indexofSiteUser() throws Exception {
		CustomUserDetails ud = new CustomUserDetails(
				new SiteUser("satou5", "", "ADMIN", ""));
		doAnswer(new Answer<Page<SiteUser>>() {
			@Override
			public Page<SiteUser> answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				return Page.empty((Pageable) args[0]);
			}
		}).when(userService).getSiteuser(any(), any(), any(), any(), any());
		mockMvc.perform(get("/admin/siteuser/list").with(user(ud)))
				.andExpect(status().isOk());
	}


}
