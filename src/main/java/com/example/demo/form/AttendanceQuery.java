package com.example.demo.form;

import lombok.Data;

@Data
public class AttendanceQuery {
	private String username;
	private Integer year;
	private Integer month;
	private Integer day;

	public AttendanceQuery() {
		username = "";
		year = 1;
		month = 1;
		day = 1;
	}

}