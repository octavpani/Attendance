package com.example.demo.form;

import lombok.Data;

@Data
public class AttendancesForm {
	private Integer number;
	private Integer year;
	private Integer month;

	public AttendancesForm() {
		number = null;
		year = null;
		month = null;

	}

}
