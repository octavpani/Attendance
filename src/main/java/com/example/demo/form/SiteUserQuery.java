package com.example.demo.form;

import lombok.Data;

@Data
public class SiteUserQuery {
	private Integer id;
	private String username;
	private String role;

	SiteUserQuery() {
		id = null;
		username = "";
		role = "";
	}
}
