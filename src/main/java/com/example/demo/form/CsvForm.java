package com.example.demo.form;

import lombok.Data;

@Data
public class CsvForm {
	private Integer year;
	private Integer month;

	public CsvForm(int year, int month) {
		this.year= year;
		this.month = month;
	}

	public CsvForm() {

	}
}
