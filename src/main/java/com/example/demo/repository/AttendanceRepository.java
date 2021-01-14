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
	@Query(value = "SELECT * FROM attendance WHERE (username LIKE :username) AND (:anyYear OR year LIKE :year) AND (:anyMonth OR month LIKE :month) AND (:anyDay OR day LIKE :day)",
			countQuery = "SELECT COUNT(*) FROM attendance WHERE (username LIKE :username) AND (:anyYear OR year LIKE :year) AND (:anyMonth OR month LIKE :month) AND (:anyDay OR day LIKE :day)",
			nativeQuery = true)
	Page<Attendance> findYourAttendance(@Param("username") String username,
			@Param("anyYear") boolean anyYear, @Param("year") Integer year,
			@Param("anyMonth") boolean anyMonth, @Param("month") Integer month,
			@Param("anyDay") boolean anyDay, @Param("day") Integer day,
			Pageable pageable);

	//勤怠時間
	List<Attendance> findByUsernameLike(String name);

	//Admin用の検索
	@Query(value = "SELECT * FROM attendance WHERE (:anyName OR username LIKE :username) AND (:anyYear OR year LIKE :year) AND (:anyMonth OR month LIKE :month) AND (:anyDay OR day LIKE :day)",
			countQuery = "SELECT COUNT(*) FROM attendance WHERE (:anyName OR username LIKE :username) AND (:anyYear OR year LIKE :year) AND (:anyMonth OR month LIKE :month) AND (:anyDay OR day LIKE :day)",
			nativeQuery = true)
	Page<Attendance> findAttendance(@Param("anyName") boolean anyName, @Param("username") String username,
			@Param("anyYear") boolean anyYear, @Param("year") Integer year,
			@Param("anyMonth") boolean anyMonth, @Param("month") Integer month,
			@Param("anyDay") boolean anyDay, @Param("day") Integer day,
			Pageable pageable);

}
