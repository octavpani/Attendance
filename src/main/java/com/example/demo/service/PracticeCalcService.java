package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Attendance;

import lombok.Getter;
import lombok.Setter;
@Service
@Getter
@Setter

public class PracticeCalcService {
	public static final int HOUR  = 60;

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

		int sum_day_st = ((attendance.getSta_hour() * HOUR) + attendance.getSta_min());
		int sum_day_end = ((attendance.getEnd_hour() * HOUR) + attendance.getEnd_min());
		int calcHours = (sum_day_end - sum_day_st) / HOUR;
		int calcMinutes = (sum_day_end - sum_day_st) % HOUR;
		if (calcMinutes < 0) {
			calcMinutes += HOUR;
			calcHours -= 1;
		}
		return new int[] {calcHours, calcMinutes};

	}

	static public boolean isValidWorkingRange(
		      Integer staHour, Integer staMin, Integer endHour,  Integer endMin) {
		  if (staHour > endHour) return false;
		  if (staHour == endHour && staMin > endMin ) return false;
		  if (staHour < 5 || staHour >= 23) return false;
		  return true;
		}


}












