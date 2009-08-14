package com.gwtent.client.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Paging<T> implements IsSerializable {

	private long totalRecords;
	private List<T> records = new ArrayList<T>();

	public Paging(){
		
	}
	
	public Paging(int totalRecords, List<T> records){
		this.totalRecords = totalRecords;
		this.records = records;
	}
	
	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public List<T> getRecords() {
		return records;
	}
}
