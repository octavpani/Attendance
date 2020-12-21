package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.example.demo.service.PracticeCalcService;

import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "attendance")
@Data
@Setter
public class Attendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Size(min = 2, max = 20)
	@Column(name="username")
	private String username;

	@Column(name="month")
	private Integer month;
	@Column(name="day1_st1")
	private int day1_st1;
	@Column(name="day1_st2")
	private int day1_st2;
	@Column(name="day1_end1")
	private int day1_end1;
	@Column(name="day1_end2")
	private int day1_end2;

	public int workingHours() {
		return PracticeCalcService.getHours(this);
	}
	public int workingMinutes() {
		return PracticeCalcService.getMinutes(this);
	}


}
