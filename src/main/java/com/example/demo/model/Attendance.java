package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.demo.service.PracticeCalcService;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "attendance")
@Data
@Setter
@Getter
public class Attendance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Size(min = 2, max = 20)
	@Column(name="username")
	private String username;

	@Min(1)
	@Max(12)
	@Column(name="month")
	private Integer month;

	@Min(1)
	@Max(31)
	@Column(name="day")
	private Integer day;

	@Min(2000)
	@Max(2099)
	@Column(name="year")
	private Integer year;

	@Min(value = 5, message = "開始時刻には、5以上23以下の数字を入力してください。")
	@Max(value = 23, message = "開始時刻には、5以上23以下の数字を入力してください。")
	@NotNull(message = "開始の時刻（Hour）を入力してください。")
	@Column(name="sta_hour")
	private Integer staHour;


	@NotNull(message = "開始の時刻(Min)を入力してください。")
	@Column(name="sta_min")
	private Integer staMin;


	@NotNull(message = "終了の時刻（Hour）を入力してください。")
	@Column(name="end_hour")
	private Integer endHour;


	@NotNull(message = "終了の時刻（Min）を入力してください。")
	@Column(name="end_min")
	private Integer endMin;



	public int workingHours() {
		return PracticeCalcService.getHours(this);
	}
	public int workingMinutes() {
		return PracticeCalcService.getMinutes(this);
	}

	public boolean isNew() {
		return id == null;
	}


}
