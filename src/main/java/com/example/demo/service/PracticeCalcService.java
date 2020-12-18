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

	public int get_sum_day_st(Attendance attendance) {
		int sum_day_st = ((attendance.getDay1_st1() * 60) + attendance.getDay1_st2() );
		return  sum_day_st;
	}
	public int get_sum_day_end(Attendance attendance) {
		int sum_day_end = ((attendance.getDay1_end1() * 60) + attendance.getDay1_end2() );
	    return  sum_day_end;
	}


	public int get_calcWH1(int sum_day_st, int sum_day_end) {
		int calcWH1 = (sum_day_end - sum_day_st) / 60 ;
		return calcWH1;
	}
	public int get_calcWH2(int sum_day_st, int sum_day_end) {
		int calcWH2 = (sum_day_end - sum_day_st) % 60 ;
		return calcWH2;
	}

}









