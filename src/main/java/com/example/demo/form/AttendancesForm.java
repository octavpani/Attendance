package com.example.demo.form;

import lombok.Data;

@Data
public class AttendancesForm {
	private String username;
	private Integer number;
	private Integer year;
	private Integer month;

	public AttendancesForm() {
		username = "";
		number = null;
		year = null;
		month = null;

	}

}
