package com.iffi;


import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.gson.*;
import com.thoughtworks.xstream.XStream;


/**
 * 
 * This is our dataConverter class.
 * This class converts csv data into json and xml format for persons and assets files.
 * 
 */

import com.thoughtworks.xstream.io.xml.DomDriver;

public class DataConverter {

	private final PersonsList personsList;
	private final List<Object> AllAssets = new ArrayList<Object>();
	private Assets Assets;
	private final List<Object> AllPersons = new ArrayList<Object>();
	
	public DataConverter(){
		this.personsList = new PersonsList();
		this.Assets = new Assets();
		loadPersons();
		loadAssets();
	}
	
	/**
	 * 
	 * 
	 * This is our Persons.csv parser. This loads in an csv of persons
	 * And outputs a json file of a person along with an xml file of information
	 * 
	 */
	
	private void loadPersons() {
		Scanner s = null;
		try {
			s = new Scanner(new File("data/Persons.csv"));
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		String firstLine = s.nextLine();
		String findTotal [] = firstLine.split(",");
		int count = Integer.parseInt(findTotal[0]);
	
		for(int i = 0; i < count; i++) {
			String line = s.nextLine();
			String tokens[] = line.split(",");
			String personCode = tokens[0];
			String lastName = tokens[1];
			String firstName = tokens[2];
			String address = tokens[3];
			String city = tokens[4];
			String state = tokens[5];
			String zip = tokens[6];
			String country = tokens[7];
			
			Person p = new Person(personCode,lastName,firstName,address,city,state,zip,country);
			personsList.addPerson(p);
			AllPersons.add(p);
			
//			if(line.isBlank() != true) {
//				String email = tokens[8];
//				personsList.persons.addEmail(email);
//				AllPersons.add(email);
//			}
//			else {
//				}
//			
//		}
	
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			Writer writer = Files.newBufferedWriter(Paths.get("data/Persons.json"));
			gson.toJson(personsList, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		XStream xstream = new XStream(new DomDriver());
		
		xstream.alias("PersonsList", List.class);
		
	
		try {
			xstream.toXML(AllPersons, new FileWriter("data/Persons.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	}

	/**
	 * 
	 * This is our Assets Parser. We load in the assets .csv file
	 * And returns an asset data file stored in both json and xml format.
	 * 
	 * 
	 */
	
	private void loadAssets() {
		Scanner s = null;
		try {
			s = new Scanner(new File("data/Assets.csv"));
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		String firstLine = s.nextLine();
		String findTotal [] = firstLine.split(",");
		int count = Integer.parseInt(findTotal[0]);
		
		for(int i = 0; i < count; i++) {
			String line = s.nextLine();
			String tokens[] = line.split(",");
			if(tokens[1].equals("P")){
				String code = tokens[0];
				String label = tokens[2];
				double appraisedValue = Double.parseDouble(tokens[3]);
				Property p = new Property(code, label, appraisedValue);
				Property b = new Property(p, LocalDate.ofYearDay(2021, 20), 20000.35);
				//System.out.println(b.getAppraisalValue());
				//System.out.println(b.getAssetCode());
				//System.out.println(b.getValue());
				//Assets.addAsset(p);
				//AllAssets.add(p);

			}
			else if(tokens[1].equals("S")) {
				String code = tokens[0];
				String label = tokens[2];
				String symbol = tokens[3];
				double sharePrice = Double.parseDouble(tokens[4]);
				Stock p = new Stock(code, label, symbol, sharePrice);
				Stock t = new Stock(p, 45, 250, 10);
				System.out.println(code);
				System.out.println(t.getValue());
				//Assets.addAsset(p);
				//AllAssets.add(p);

			}
			else if(tokens[1].equals("C")) {
				String code = tokens[0];
				String label = tokens[2];
				double exchangeRate = Double.parseDouble(tokens[3]);
				double exchangeFeeRate = Double.parseDouble(tokens[4]);
				//Crypto p = new Crypto(code, label, exchangeRate, exchangeFeeRate);
				//Assets.addAsset(p);
				//AllAssets.add(p);
			}
			
			
			else {
				throw new RuntimeException("Asset code invalid");
			}
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			Writer writer = Files.newBufferedWriter(Paths.get("data/Assets.json"));
			gson.toJson(Assets, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		XStream xstream = new XStream(new DomDriver());
		
		xstream.alias("Property", Property.class);
		xstream.alias("Stock", Stock.class);
		xstream.alias("Crypto", Crypto.class);
		xstream.alias("Assets", List.class);
		

		try {
			xstream.toXML(AllAssets, new FileWriter("data/Assets.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		s.close();
	}

	public static void main(String[] args) {
		DataConverter demo = new DataConverter();
    }
}












	
