package com.example.demo.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.example.demo.model.Attendance;

import lombok.Data;
@Data
public class AttendancesCreationDto {
	@Valid
	 private List<Attendance> attendances = new ArrayList<Attendance>();

	 public void addAttendance(Attendance attendance) {
	        this.attendances.add(attendance);
	    }

}
