package com.example.demo.form;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
public class IdListForEdit {
	 private List<String> idList = new ArrayList<String>();

	 public void addId(String idForEdit) {
	        this.idList.add(idForEdit);
	    }

}