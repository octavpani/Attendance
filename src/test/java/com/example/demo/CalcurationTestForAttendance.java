package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.Attendance;
import com.example.demo.service.PracticeCalcService;

@SpringBootTest
public class CalcurationTestForAttendance {

	@Autowired
	PracticeCalcService pcs;

	@Test
	void isValidTest() {
		List<Attendance> attendances = new ArrayList<Attendance>();
		//開始時刻の方が終了時刻よりも大きい
		Attendance attendance1 = new Attendance("Admin_satou", 2023, 1, 1, 19, 30, 17, 30);
		//Hourが等しく、開始時刻のMinが、終了時刻のMinよりも大きい
		Attendance attendance2 = new Attendance("Admin_satou", 2023, 1, 1, 17, 45, 17, 30);
		//23時以降、4時以前に勤務開始
		Attendance attendance3 = new Attendance("Admin_satou", 2023, 1, 1, 23, 30, 17, 30);
		attendances.add(attendance1);
		attendances.add(attendance2);
		attendances.add(attendance3);
		for (Attendance attendance : attendances) {
			Boolean valid = pcs.isValidWorkingRange(attendance.getStaHour(), attendance.getStaMin(),
					attendance.getEndHour(), attendance.getEndMin());
			assertThat(valid.booleanValue()).isFalse();
		}
	}

	@Test
	void isValidTestForList() {
		List<Attendance> attendances = new ArrayList<Attendance>();
		//開始時刻の方が終了時刻よりも大きい
		Attendance attendance1 = new Attendance("Admin_satou", 2023, 1, 1, 19, 30, 17, 30);
		//Hourが等しく、開始時刻のMinが、終了時刻のMinよりも大きい
		Attendance attendance2 = new Attendance("Admin_satou", 2023, 1, 1, 17, 45, 17, 30);
		//23時以降、4時以前に勤務開始
		Attendance attendance3 = new Attendance("Admin_satou", 2023, 1, 1, 23, 30, 17, 30);
		attendances.add(attendance1);
		attendances.add(attendance2);
		attendances.add(attendance3);
		Boolean valid = pcs.isValidWorkingRange(attendances);
		assertThat(valid.booleanValue()).isFalse();

	}

	@Test
	void calcurateTime() {
		//一覧表示での合計の勤怠時間の計算。
		List<Attendance> attendances = new ArrayList<Attendance>();
		Attendance attendance1 = new Attendance("Admin_satou", 2023, 1, 1, 7, 30, 17, 30);
		Attendance attendance2 = new Attendance("Admin_satou", 2023, 1, 1, 8, 0, 18, 30);
		Attendance attendance3 = new Attendance("Admin_satou", 2023, 1, 1, 9, 30, 19, 30);
		attendances.add(attendance1);
		attendances.add(attendance2);
		attendances.add(attendance3);
		int sumTime = 0;
		for (Attendance attendance : attendances) {
			sumTime = sumTime + attendance.workingHours() * pcs.HOUR + attendance.workingMinutes();
		}
		int sumHours = pcs.hourFromSumTime(sumTime);
		int sumMinutes = pcs.minFromSumTime(sumTime);
		assertThat(sumHours).isEqualTo(30);
		assertThat(sumMinutes).isEqualTo(30);
	}

}
