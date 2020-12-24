package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.Attendance;

import lombok.Getter;
import lombok.Setter;
@Service
@Getter
@Setter

public class PracticeCalcService {
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

		int sum_day_st = ((attendance.getSta_hour() * hour) + attendance.getSta_min());
		int sum_day_end = ((attendance.getEnd_hour() * hour) + attendance.getEnd_min());
		int calcHours = (sum_day_end - sum_day_st) / hour;
		int calcMinutes = (sum_day_end - sum_day_st) % hour;
		if (calcMinutes < 0) {
			calcMinutes += 60;
			calcHours -= 1;
		}
		return new int[] {calcHours, calcMinutes};

	}
	/* 作成途中
	 *
	 *  Map<int, int> calc = new LinkedHashMap<int, int>();
	    for (i = 1; i <= 31; i++) {

	    int sum_day_st = ((attendance.getDay + i + _st1() * hour) + attendance.getDay + i + _st2());
		int sum_day_end = ((attendance.getDay + i + _end1() * hour) + attendance.getDay + i + _end2());
		int calcHours = (sum_day_end - sum_day_st) / hour;
		int calcMinutes = (sum_day_end - sum_day_st) % hour;
		if (calcMinutes < 0) {
			calcMinutes += 60;
			calcHours -= 1;
		}
		calc.put(calcHours, calcMinutes);

	}
	    return calc;


	}
 */
	static public boolean isValidWorkingRange(
		      Integer staHour, Integer staMin, Integer endHour,  Integer endMin) {
		  if (staHour > endHour) return false;
		  if (staHour == endHour && staMin > endMin ) return false;
		  if (staHour < 5 || staHour >= 23) return false;
		  return true;
		}


}












