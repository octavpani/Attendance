package com.example.demo.form;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class IdListForSiteUser {
	private List<String> idList = new ArrayList<String>();

	public void addId(String idForSiteUser) {
		this.idList.add(idForSiteUser);
	}

	public void setId(int num, String id) {
		 idList.set(num, id);
	 }

}
