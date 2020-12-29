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
	    int sumDaySta = convertTime(attendance.getStaHour(), attendance.getStaMin());
		int sumDayEnd = convertTime(attendance.getEndHour(), attendance.getEndMin());
		int calcHours = getHours(sumDaySta, sumDayEnd);
		int calcMinutes = getMinutes(sumDaySta, sumDayEnd);
		if (calcMinutes < 0) {
			calcMinutes += HOUR;
			calcHours -= 1;
		}
		return new int[] {calcHours, calcMinutes};

		/*
		int calcHours = (sumDayEnd - sumDaySta) / HOUR;
		int calcMinutes = (sumDayEnd - sumDaySta) % HOUR;
		if (calcMinutes < 0) {
			calcMinutes += HOUR;
			calcHours -= 1;
			*/


		/*
		 int sumDaySta = ((attendance.getStaHour() * HOUR) + attendance.getStaMin());
			int sumDayEnd = ((attendance.getEndHour() * HOUR) + attendance.getEndMin());
			☑// [gpsoft]↑変数名もcamelCaseの方が良いです。
			convertTime(attendance);
			int calcHours = (sumDayEnd - sumDaySta) / HOUR;
			int calcMinutes = (sumDayEnd - sumDaySta) % HOUR;
		*/


		// [gpsoft]
		// 以下の3つは、よく使う演算なので、独立したメソッドにした方が良いのでは?
		//☑   - hourとminから、「分単位」へ変換する(hour * 60 + min)
		//☑  - 「分単位」から、hourを取り出す
		//☑   - 「分単位」から、minを取り出す

	}

	static public boolean isValidWorkingRange(
		      Integer staHour, Integer staMin, Integer endHour,  Integer endMin) {
		  if (staHour > endHour) return false;
		  if (staHour == endHour && staMin > endMin ) return false;
		  if (staHour < 5 || staHour >= 23) return false;
		  return true;
		}

	static public Integer convertTime(Integer hour,  Integer min) {
		 return hour * HOUR + min;
	}

	static public Integer getHours(Integer sumSta,  Integer sumEnd) {
	     return (sumEnd - sumSta) / HOUR;
}

	static public Integer getMinutes(Integer sumSta,  Integer sumEnd) {
	     return (sumEnd - sumSta) % HOUR;


	}
}












