package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Attendance;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
	Page<Attendance> findByUsernameLikeAndMonthIs(String username, Integer month, Pageable pageable);


	//user用の検索

	Page<Attendance> findByYearIsAndUsernameLike(Integer year, String name, Pageable pageable);

	Page<Attendance> findByMonthIsAndUsernameLike(Integer month, String name, Pageable pageable);

	Page<Attendance> findByDayIsAndUsernameLike(Integer day, String name, Pageable pageable);

	Page<Attendance> findByYearIsAndDayIsAndUsernameLike(Integer year, Integer day, String name, Pageable pageable);

	Page<Attendance> findByYearIsAndMonthIsAndUsernameLike(Integer year, Integer month, String name, Pageable pageable);

	Page<Attendance> findByYearIsAndMonthIsAndDayIsAndUsernameLike(Integer year, Integer month, Integer day, String name, Pageable pageable);

	Page<Attendance> findByMonthIsAndDayIsAndUsernameLike(Integer month, Integer day, String name, Pageable pageable);

	Page<Attendance> findByUsernameLike(String name, Pageable pageable);

	List<Attendance> findByUsernameLike(String name);




	//Admin用の検索

	Page<Attendance> findByYearIsAndMonthIsAndDayIs(Integer year, Integer month, Integer day, Pageable pageable);

	Page<Attendance> findByMonthIsAndDayIs(Integer month, Integer day, Pageable pageable);

	Page<Attendance> findByYearIsAndMonthIs(Integer year, Integer month, Pageable pageable);

	Page<Attendance> findByYearIsAndDayIs(Integer year, Integer day, Pageable pageable);

	Page<Attendance> findByYearIs(Integer year, Pageable pageable);

	Page<Attendance> findByMonthIs(Integer month, Pageable pageable);

	Page<Attendance> findByDayIs(Integer day, Pageable pageable);

	@Query(value = "SELECT * FROM attendance WHERE (:anyName OR username LIKE :username) AND (:anyYear OR year IS :year) AND (:anyMonth OR month IS :month) AND (:anyDame OR Day IS :day)",
			countQuery = "SELECT COUNT(*) FROM attendance WHERE  (:anyName OR username LIKE :username) AND (:anyYear OR year IS :year) AND (:anyMonth OR month IS :month) AND (:anyDame OR Day IS :day)",
			nativeQuery = true)
	Page<Attendance> find(@Param("anyName") boolean anyName, @Param("username") String username,
			@Param("anyYear") boolean anyYear, @Param("year") Integer year,
			@Param("anyMonth") boolean anyMonth, @Param("month") Integer month,
			@Param("anyDay") boolean anyDay, @Param("day") Integer day,
			Pageable pageable);




}
