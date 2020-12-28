package com.example.demo.form;

import lombok.Data;

@Data
public class AttendanceQuery {
	private String username;
	private int month;

	public AttendanceQuery() {
		username = "";
		month = 1;
	}

}