
package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
@Table(name = "siteuser")
public class SiteUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="username")
	@Size(min = 2, max = 20)
	private String username;

	@Column(name="password")
	@Size(min = 4, max = 255)
	private String password;

	@Column(name="role")
	private String role;

	@Lob
	@Column(name="avatar")
	private String avatar;
}
