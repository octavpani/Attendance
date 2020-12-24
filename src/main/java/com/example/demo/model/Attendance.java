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
	@Size(min = 1, max = 12)
	private Integer month;

	@Column(name="day")
	@Size(min = 1, max = 31)
	private Integer day;
	@Column(name="year")
	@Size(min = 2000, max = 2099)
	private Integer year;
	@Column(name="sta_hour")
	private Integer sta_hour;
	@Column(name="sta_min")
	private Integer sta_min;
	@Column(name="end_hour")
	private Integer end_hour;
	@Column(name="end_min")
	private Integer end_min;



	public int workingHours() {
		return PracticeCalcService.getHours(this);
	}
	public int workingMinutes() {
		return PracticeCalcService.getMinutes(this);
	}


}
