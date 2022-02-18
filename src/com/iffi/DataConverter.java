package com.iffi;


import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

	//private final personsList pList;
	private final List<Object> AllAssets = new ArrayList<Object>();
	private Assets Assets;
	
	public DataConverter(){
		//this.pList = new personsList();
		this.Assets = new Assets();
		//loadPersons();
		loadAssets();
	}
	
//	private void loadPersons() {
//		Scanner s = null;
//		try {
//			s = new Scanner(new File("Data/Persons.csv"));
//		}
//		catch (FileNotFoundException e) {
//			throw new RuntimeException(e);
//		}
//		
//		int count = Integer.parseInt(s.nextLine());
//		for(int i = 0; i <= count; i++) {
//			String line = s.nextLine();
//			String tokens[] = line.split(",");
//			String personCode = tokens[0];
//			String lastName = tokens[1];
//			String firstName = tokens[2];
//			String address = tokens[3];
//			String city = tokens[4];
//			String state = tokens[5];
//			String zip = tokens[6];
//			String country = tokens[7];
//			//To Do: figure out how to read in the emails if theres multiple
//			//String email = tokens
//			
//			Person p = new Person(personCode,lastName,firstName,address,city,state,zip,country,email);
//			p.setPersonCode(personCode);
//			p.setLastName(lastName);
//			p.setFirstName(firstName);
//			p.setAddress(address);
//			p.setCity(city);
//			p.setState(state);
//			p.setZip(zip);
//			p.setCountry(country);
//			p.setEmail(email);
//			pList.addperson(p);
//
//		}
//	}
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
				Assets.addAsset(p);
				AllAssets.add(p);

			}
			else if(tokens[1].equals("S")) {
				String code = tokens[0];
				String label = tokens[2];
				String symbol = tokens[3];
				double sharePrice = Double.parseDouble(tokens[4]);
				Stock p = new Stock(code, label, symbol, sharePrice);
				Assets.addAsset(p);
				AllAssets.add(p);

			}
			else if(tokens[1].equals("C")) {
				String code = tokens[0];
				String label = tokens[2];
				double exchangeRate = Double.parseDouble(tokens[3]);
				double exchangeFeeRate = Double.parseDouble(tokens[4]);
				Crypto p = new Crypto(code, label, exchangeRate, exchangeFeeRate);
				Assets.addAsset(p);
				AllAssets.add(p);
			}
			
			
			else {
				throw new RuntimeException("Asset code invalid");
			}
		s.close();
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
		
	}
	
	public static void main(String[] args) {
		DataConverter demo = new DataConverter();
    }
}












	
