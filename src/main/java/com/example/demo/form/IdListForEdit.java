package com.example.demo.form;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
public class IdListForEdit {
	 private List<IdForEdit> idList = new ArrayList<IdForEdit>();

	 public void addId(IdForEdit idForEdit) {
	        this.idList.add(idForEdit);
	    }

}