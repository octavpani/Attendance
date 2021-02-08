package com.example.demo;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HttpRequestsTests {

	@LocalServerPort
	private int port;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private TestRestTemplate restTemplate;

	private String url(String path) {
		return "http://localhost:" + port + path;
	}

	private String get(String path) {
		return restTemplate.getForObject(url(path), String.class);
	}

	/*@Test
	public void landingPage() throws Exception {
		Document doc = Jsoup.parse(get("/")); */
	@Test
	@WithMockUser("suzuki")
	public void landingPage() throws Exception {
		String body = mvc.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		Document doc = Jsoup.parse(body);

		//assertThat(doc.select("table.top tr").size()).as("メニューアイテム数").isEqualTo(3);
		//assertThat(doc.select("h2").text()).as("ログイン画面").isEqualTo("ログイン画面");
		assertThat(doc.select("a").text()).as("メニュー")
				.contains("作成へ")
				.contains("一括作成へ");
	}

}
