package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Attendance;

import lombok.Getter;
import lombok.Setter;
@Service
@Getter
@Setter

public class PracticeCalcService {
	static Attendance attendance;
	public static final int hour  = 60;

	static public int getHours(Attendance attendance) {
		calc(attendance);
		int[] data = calc(attendance);
		return data[0];
	}

	static public int getMinutes(Attendance attendance) {
		calc(attendance);
		int[] data = calc(attendance);
		return data[1];
	}

	static public int[] calc(Attendance attendance) {

		int sum_day_st = ((attendance.getDay1_st1() * hour) + attendance.getDay1_st2());
		int sum_day_end = ((attendance.getDay1_end1() * hour) + attendance.getDay1_end2());
		int calcHours = (sum_day_end - sum_day_st) / hour;
		int calcMinutes = (sum_day_end - sum_day_st) % hour;
		if (calcMinutes < 0) {
			calcMinutes += 60;
			calcHours -= 1;
		}
		return new int[] {calcHours, calcMinutes};

	}

	static public boolean isValidWorkingRange(
		      int staHour, int staMin, int endHour, int endMin) {
		  if (staHour > endHour) return false;
		  if (staHour == endHour && staMin > endMin ) return false;
		  if (staHour < 5 || staHour >= 23) return false;
		  return true;
		}


}












