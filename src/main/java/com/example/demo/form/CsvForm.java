package com.example.demo.form;

import lombok.Data;

@Data
public class CsvForm {
	private Integer year;
	private Integer month;

	CsvForm() {
		year = null;
		month = null;
	}
}
