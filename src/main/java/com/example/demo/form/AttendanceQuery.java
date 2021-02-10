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
		year = null;
		month = null;
		day = null;
	}

	public AttendanceQuery(String username, Integer year, Integer month, Integer day) {
		this.username = username;
		this.year = year;
		this.month = month;
		this.day = day;
	}

}