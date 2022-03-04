package com.iffi;

import java.util.ArrayList;
import java.util.List;

/**
 * This is our PersonsList object.
 * This stores each person in a list before outputting it as a json/xml file.
 * 
 * @author Martin Herz, Michael Endacott
 *
 */


public class PersonsList {

	private List<Object> persons;
		
	public PersonsList() {
		this.persons = new ArrayList<Object>();
	}
		
	public List<Object> getPersonsList(){
		return this.persons;
	}
		
	public void addPerson(Object Person) {
			this.persons.add(Person);
	}
	}


