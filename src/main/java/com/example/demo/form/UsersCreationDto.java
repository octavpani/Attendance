package com.example.demo.form;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.example.demo.model.SiteUser;

import lombok.Data;
@Data

public class UsersCreationDto {
	 @Valid
	 private List<SiteUser> users = new ArrayList<SiteUser>();

	 public void addSiteUser(SiteUser user) {
	        this.users.add(user);
	    }

}
