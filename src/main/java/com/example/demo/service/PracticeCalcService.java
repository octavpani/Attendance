package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Attendance;

import lombok.Getter;
import lombok.Setter;
@Service
@Getter
@Setter

public class PracticeCalcService {
	private int sum_day_end;
	private int sum_day_st;
	Attendance attendance;
	public static final int hour  = 60;

	static public int get_calcHours(Attendance attendance) {
		int sum_day_st = ((attendance.getDay1_st1() * hour) + attendance.getDay1_st2());
		int sum_day_end = ((attendance.getDay1_end1() * hour) + attendance.getDay1_end2());
		int calcHours = (sum_day_end - sum_day_st) / hour;
		int calcMinutes = (sum_day_end - sum_day_st) % hour;

		if (calcHours < 1) {
			calcHours += 24;
		}

		if (calcHours > 23) {
			calcHours = 0;
			calcMinutes = 0;
		}

		if (calcMinutes < 0) {
			calcMinutes += 60;
			calcHours -= 1;
		}

		return calcHours;
	}

	static public int get_calcMinutes(Attendance attendance) {
		int sum_day_st = ((attendance.getDay1_st1() * hour) + attendance.getDay1_st2());
		int sum_day_end = ((attendance.getDay1_end1() * hour) + attendance.getDay1_end2());
		int calcHours = (sum_day_end - sum_day_st) / hour;
		int calcMinutes = (sum_day_end - sum_day_st) % hour;

		if (calcHours < 1) {
			calcHours += 24;
		}

		if (calcHours > 23) {
			calcHours = 0;
			calcMinutes = 0;
		}

		if (calcMinutes < 0) {
			calcMinutes += 60;
			calcHours -= 1;
		}

		return calcMinutes;
	}


}









