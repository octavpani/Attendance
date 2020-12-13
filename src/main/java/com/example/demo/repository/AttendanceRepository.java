package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Attendance;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
	//List<Attendance> findByMonthLike(int month);
	List<Attendance> findByUsernameLikeAndMonthIs(String username, Integer month);


	//Attendance findByUsername(String username);
	//boolean existsByUsername(String username);


}
