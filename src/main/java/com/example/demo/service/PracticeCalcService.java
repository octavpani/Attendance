package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Attendance;

import lombok.Getter;
import lombok.Setter;
@Service
@Getter
@Setter
public class PracticeCalcService {
	public static final int HOUR  = 60;
	//Attendanceのメソッド。勤務時間（Hour）を計算する。
	static public int getHours(Attendance attendance) {
		int[] data = calcWorkingTime(attendance);
		return data[0];
	}

	//Attendanceのメソッド。勤務時間（Min）を計算する。
	static public int getMinutes(Attendance attendance) {
		int[] data = calcWorkingTime(attendance);
		return data[1];
	}
	//上記のメソッドで利用
	static public int[] calcWorkingTime(Attendance attendance) {
	    int sta = sumTimeFromTime(attendance.getStaHour(), attendance.getStaMin());
		int end = sumTimeFromTime(attendance.getEndHour(), attendance.getEndMin());
		int elapse = end - sta;
		return new int[] {hourFromSumTime(elapse), minFromSumTime(elapse)};
	}

	static public boolean isValidWorkingRange(
		      Integer staHour, Integer staMin, Integer endHour,  Integer endMin) {
		  if (staHour > endHour) return false;
		  if (staHour == endHour && staMin > endMin ) return false;
		  if (staHour < 5 || staHour >= 23) return false;
		  return true;
		}

	static public boolean isValidWorkingRange(List<Attendance> attendances) {
		for(Attendance attendance : attendances) {
			if (attendance.getStaHour() > attendance.getEndHour()) return false;
			if (attendance.getStaHour() == attendance.getEndHour() && attendance.getStaMin() > attendance.getEndMin() ) return false;
			if (attendance.getStaHour() < 5 || attendance.getStaHour() >= 23) return false;
			}
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