package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestsTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String url(String path) {
		return "http://localhost:" + port + path;
	}

	private String get(String path) {
		return restTemplate.getForObject(url(path), String.class);
	}

	@Test
	public void landingPage() throws Exception {
		Document doc = Jsoup.parse(get("/"));

		//assertThat(doc.select("table.top tr").size()).as("メニューアイテム数").isEqualTo(3);
		//assertThat(doc.select("h2").text()).as("ログイン画面").isEqualTo("ログイン画面");
		assertThat(doc.select("a").text()).as("メニュー")
				.contains("作成へ")
				.contains("一括作成へ");
	}

}
