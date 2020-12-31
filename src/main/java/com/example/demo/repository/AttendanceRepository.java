package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Attendance;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
	Page<Attendance> findByUsernameLikeAndMonthIs(String username, Integer month, Pageable pageable);

	/* 作成中　月のみ名前のみ
	List<Attendance> findByUsernameLike(String username);

	List<Attendance> findByMonth(Integer month);
	*/



}
