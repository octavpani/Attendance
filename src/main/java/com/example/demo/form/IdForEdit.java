package com.example.demo.form;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
public class IdForEdit {
	 private List<Long> idList = new ArrayList<Long>();

	 public void addId(Long id) {
	        this.idList.add(id);
	    }

}
