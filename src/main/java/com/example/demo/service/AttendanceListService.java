package com.example.demo.service;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.form.AttendanceQuery;
import com.example.demo.model.Attendance;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class AttendanceListService {
	private final AttendanceService attendanceService;
	/* 最初のメソッド 動作確認済み
	public Page<Attendance> SelectAttendanceListForAdmin(Pageable pageable, AttendanceQuery attendanceQuery,
			String username, Integer year, Integer month, Integer day) {
		Page<Attendance> attendances = null;
		if(year == null && day == null && month == null && !(StringUtils.hasLength(username))) {
			attendances = attendanceService.getAllAttendance(pageable);
		} else if(year == null && month == null && day == null) {
			attendances = attendanceService.getAttendanceByUsernameLike(pageable, attendanceQuery);
		} else if(!(StringUtils.hasLength(username)) && year == null && month == null) {
			attendances = attendanceService.getAttendanceByDayIs(pageable, attendanceQuery);
		} else if(!(StringUtils.hasLength(username)) && year == null && day == null) {
			attendances = attendanceService.getAttendanceByMonthIs(pageable, attendanceQuery);
		} else if(!(StringUtils.hasLength(username)) && month == null && day == null) {
			attendances = attendanceService.getAttendanceByYearIs(pageable, attendanceQuery);
		} else if(month == null && day == null)  {
			attendances = attendanceService.getAttendanceByYearIsAndUsernameLike(pageable, attendanceQuery);
		} else if(year == null && day == null) {
			attendances = attendanceService.getAttendanceByMonthIsAndUsernameLike(pageable, attendanceQuery);
		} else if(year == null && month == null) {
			attendances = attendanceService.getAttendanceByDayIsAndUsernameLike(pageable, attendanceQuery);
		} else if(!(StringUtils.hasLength(username)) && day == null) {
			attendances = attendanceService.getAttendanceByYearIsAndMonthIs(pageable, attendanceQuery);
		} else if(!(StringUtils.hasLength(username)) && month == null) {
			attendances = attendanceService.getAttendanceByYearIsAndDayIs(pageable, attendanceQuery);
		} else if(!(StringUtils.hasLength(username)) && year == null) {
			attendances = attendanceService.getAttendanceByMonthIsAndDayIs(pageable, attendanceQuery);
		} else if(!(StringUtils.hasLength(username))) {
			//正常に動作せず
			attendances = attendanceService.getAttendanceByYearIsAndMonthIsAndDayIs(pageable, attendanceQuery);
		} else if(day == null) {
			//正常に動作せず
			attendances = attendanceService.getAttendanceByYearIsAndMonthIsAndUsernameLike(pageable, attendanceQuery);
		} else if(month == null) {
			//正常に動作せず
			attendances = attendanceService.getAttendanceByYearIsAndDayIsAndUsernameLike(pageable, attendanceQuery);
		} else if(year == null) {
			attendances = attendanceService.getAttendanceByMonthIsAndDayIsAndUsernameLike(pageable, attendanceQuery);
		} else {
			//正常に動作せず　デッドコードとの警告あり
			attendances = attendanceService.getAttendanceByYearIsAndMonthIsAndDayIsAndUsernameLike(pageable, attendanceQuery);
		}

		return attendances;
} */ //ローカル変数バージョン
	public Page<Attendance> SelectAttendanceListForAdmin(Pageable pageable, AttendanceQuery attendanceQuery,
			String username, Integer year, Integer month, Integer day) {
		Page<Attendance> attendances = null;
		  boolean noYear = year == null;
		  boolean noMonth = month == null;
		  boolean noDay = day == null;
		  boolean noName = !StringUtils.hasLength(username);

		  if (noYear && noMonth && noDay && noName) {
			  attendances = attendanceService.getAllAttendance(pageable);
		  } else if (noYear && noMonth && noDay) {
			  attendances = attendanceService.getAttendanceByUsernameLike(pageable, attendanceQuery);
		  } else if (noYear && noMonth && noName) {
			  attendances = attendanceService.getAttendanceByDayIs(pageable, attendanceQuery);
		  } else if (noYear && noDay && noName) {
			  attendances = attendanceService.getAttendanceByMonthIs(pageable, attendanceQuery);
		  } else if (noMonth && noDay && noName) {
			  attendances = attendanceService.getAttendanceByYearIs(pageable, attendanceQuery);
		  } else if (noMonth && noDay) {
			  attendances = attendanceService.getAttendanceByYearIsAndUsernameLike(pageable, attendanceQuery);
		  } else if (noYear && noDay) {
			  attendances = attendanceService.getAttendanceByMonthIsAndUsernameLike(pageable, attendanceQuery);
		  } else if (noYear && noMonth) {
			  attendances = attendanceService.getAttendanceByDayIsAndUsernameLike(pageable, attendanceQuery);
		  } else if (noDay && noName) {
			  attendances = attendanceService.getAttendanceByYearIsAndMonthIs(pageable, attendanceQuery);
		  } else if (noMonth && noName) {
			  attendances = attendanceService.getAttendanceByYearIsAndDayIs(pageable, attendanceQuery);
		  } else if (noYear && noName) {
			  attendances = attendanceService.getAttendanceByMonthIsAndDayIs(pageable, attendanceQuery);
		  } else if (noName) {
			  attendances = attendanceService.getAttendanceByYearIsAndMonthIsAndDayIs(pageable, attendanceQuery);
		  } else if (noDay) {
			  attendances = attendanceService.getAttendanceByYearIsAndMonthIsAndUsernameLike(pageable, attendanceQuery);
		  } else if (noMonth) {
			  attendances = attendanceService.getAttendanceByYearIsAndDayIsAndUsernameLike(pageable, attendanceQuery);
		  } else if (noYear) {
			  attendances = attendanceService.getAttendanceByMonthIsAndDayIsAndUsernameLike(pageable, attendanceQuery);
		  } else {
			  attendances = attendanceService.getAttendanceByYearIsAndMonthIsAndDayIsAndUsernameLike(pageable, attendanceQuery);
		  }
		  return attendances;
	}


	public Page<Attendance> SelectAttendanceListForUser(Pageable pageable, Principal principal, AttendanceQuery attendanceQuery,
			Integer year, Integer month, Integer day) {

		Page<Attendance> attendances = null;
		if(year == null && month == null  && day == null) {
			attendances = attendanceService.getYourAllAttendance(principal, pageable);
		} else if(year == null && month == null) {
			attendances = attendanceService.getYourAttendanceByDay(pageable, attendanceQuery, principal);
		} else if(year == null && day == null) {
			attendances = attendanceService.getYourAttendanceByMonth(pageable, attendanceQuery, principal);
		} else if(month == null && day == null) {
			attendances = attendanceService.getYourAttendanceByYear(pageable, attendanceQuery, principal);
		} else if(year == null) {
			attendances = attendanceService.getYourAttendanceByMonthIsAndDayIs(pageable, attendanceQuery, principal);
		} else if(month == null) {
			attendances = attendanceService.getYourAttendanceByYearIsAndDayIs(pageable, attendanceQuery, principal);
		} else if(day == null){
			attendances = attendanceService.getYourAttendanceByYearIsAndMonthIs(pageable, attendanceQuery, principal);
		} else {
			attendances = attendanceService.getYourAttendanceByYearIsAndMonthIsAndDayIs(pageable, attendanceQuery, principal);
		}
		return attendances;

	}

}
