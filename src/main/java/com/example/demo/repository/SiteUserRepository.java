package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.SiteUser;

public interface SiteUserRepository extends JpaRepository<SiteUser, Integer> {
	//securityで使用
	SiteUser findByUsername(String username);
	boolean existsByUsername(String username);

	//user検索用メソッド
	@Query(value = "SELECT * FROM siteuser WHERE (:anyId OR id LIKE :id) AND (:anyUsername OR username LIKE :username) AND (:anyRole OR role LIKE :role)",
			countQuery = "SELECT COUNT(*) FROM siteuser WHERE (:anyId OR id LIKE :id) AND (:anyUsername OR username LIKE :username) AND (:anyRole OR role LIKE :role)",
			nativeQuery = true)
	Page<SiteUser> findUser(@Param("anyId") boolean anyId, @Param("id") Integer id,
			@Param("anyUsername") boolean anyUsername, @Param("username") String username,
			@Param("anyRole") boolean anyRole, @Param("role") String role,
			Pageable pageable);

}
