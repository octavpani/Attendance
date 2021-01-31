package com.example.demo.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import lombok.Data;
@Data
public class SiteUsersDto {
	 @Valid
	 private List<SiteUserForm> users = new ArrayList<SiteUserForm>();

	 public void addSiteUser(SiteUserForm user) {
		 this.users.add(user);
	    }



}
