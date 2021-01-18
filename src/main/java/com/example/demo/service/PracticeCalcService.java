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
	    int sta = sumTimeFromTime(attendance.getStaHour(), attendance.getStaMin());
		int end = sumTimeFromTime(attendance.getEndHour(), attendance.getEndMin());
		int elapse = end - sta;
		/*
		if (calcMinutes < 0) {
			calcMinutes += HOUR;
			calcHours -= 1;
		}
		*/
		return new int[] {hourFromSumTime(elapse), minFromSumTime(elapse)};



	}

	static public boolean isValidWorkingRange(
		      Integer staHour, Integer staMin, Integer endHour,  Integer endMin) {
		  if (staHour > endHour) return false;
		  if (staHour == endHour && staMin > endMin ) return false;
		  if (staHour < 5 || staHour >= 23) return false;
		  return true;
		}

	static public Integer sumTimeFromTime(Integer hour,  Integer min) {
		 return hour * HOUR + min;
	}

	static public Integer hourFromSumTime(Integer sumTime) {
	     return (sumTime) / HOUR;
}

	static public Integer minFromSumTime(Integer sumTime) {
	     return (sumTime) % HOUR;


	}
}












