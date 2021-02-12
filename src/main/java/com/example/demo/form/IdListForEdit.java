package com.example.demo.form;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Setter
@Getter
public class IdListForEdit {
	 private List<String> idList = new ArrayList<String>();

	 public void addId(String idForEdit) {
	        this.idList.add(idForEdit);
	    }

	 public void setId(int num, String id) {
		 idList.set(num, id);
	 }

}