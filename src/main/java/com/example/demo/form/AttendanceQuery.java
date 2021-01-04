package com.example.demo.form;

import lombok.Data;

@Data
public class AttendanceQuery {
	private String username;
	private Integer month;
	private Integer day;

	public AttendanceQuery() {
		username = "";
		month = 1;
		day = 1;
	}

}