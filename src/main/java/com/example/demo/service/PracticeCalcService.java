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
		int[] data = calcWorkingTime(attendance);
		return data[0];
	}

	static public int getMinutes(Attendance attendance) {
		int[] data = calcWorkingTime(attendance);
		return data[1];
	}

	static public int[] calcWorkingTime(Attendance attendance) {

		int sumOfDaySta = ((attendance.getStaHour() * HOUR) + attendance.getStaMin());
		int sumOfDayEnd = ((attendance.getEndHour() * HOUR) + attendance.getEndMin());
		int calcHours = (sumOfDayEnd - sumOfDaySta) / HOUR;
		int calcMinutes = (sumOfDayEnd - sumOfDaySta) % HOUR;
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












