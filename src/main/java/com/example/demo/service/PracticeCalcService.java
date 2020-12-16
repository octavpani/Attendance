package com.example.demo.service;

import com.example.demo.model.Attendance;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PracticeCalcService {
	private int calcWH1;
	private int calcWH2;

	public int sum_day_st(Attendance attendance) {
		int sum_day_st = ((attendance.getDay1_st1() * 60) + attendance.getDay1_st2() );
		return  sum_day_st;
	}
	public int sum_day_end(Attendance attendance) {
		int sum_day_end = ((attendance.getDay1_end1() * 60) + attendance.getDay1_end2() );
	    return  sum_day_end;

	}
	public int calcWH1(int sum_day_st, int sum_day_end) {
		int calcWH1 = (sum_day_end - sum_day_st) / 60 ;
		return calcWH1;
	}
	public int calcWH2(int sum_day_st, int sum_day_end) {
		int calcWH2 = (sum_day_end - sum_day_st) % 60 ;
		return calcWH2;
	}

}
