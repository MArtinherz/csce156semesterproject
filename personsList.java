package com.iffi;

import java.util.ArrayList;
import java.util.List;

public class personsList {
	private List<Object> persons;
	
	public personsList () {
		this.persons = new ArrayList<Object>();
	}
	public List<Object> getPersonsList(){
		return this.persons;
	}
	public void addPerson(Object Person) {
		this.persons.add(Person);
	}
}
