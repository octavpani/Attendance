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
		year = 0;
		month = 0;
		day = 0;
	}

	public AttendanceQuery(String username, Integer year, Integer month, Integer day) {
		this.username = username;
		this.year = year;
		this.month = month;
		this.day = day;
	}

}