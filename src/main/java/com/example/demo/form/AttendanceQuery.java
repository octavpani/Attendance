package com.example.demo.form;

import lombok.Data;

@Data
public class AttendanceQuery {
	private String username;
	private int month;
	//↓ここを増やしてます
	private int day;

	public AttendanceQuery() {
		username = "";
		month = 1;
		day = 1;
	}

}