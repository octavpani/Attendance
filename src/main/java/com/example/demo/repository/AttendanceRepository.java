package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
	Attendance findByUsername(String username);
	boolean existsByUsername(String username);


}
