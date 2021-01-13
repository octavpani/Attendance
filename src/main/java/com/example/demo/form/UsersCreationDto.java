package com.example.demo.form;

import java.util.List;

import com.example.demo.model.SiteUser;

import lombok.Data;
@Data
public class UsersCreationDto {
	 private List<SiteUser> users;

	 public void addSiteUser(SiteUser user) {
	        this.users.add(user);
	    }

}
