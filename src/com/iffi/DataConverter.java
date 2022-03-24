package com.iffi;


/**
 * This is our Property class.
 * This stores in the data for any type of Property asset
 * And extends off the Asset class
 * 
 * @author Martin Herz 
 * @author Michael Endacott
 * Date: 2022/02/19
 *
 */
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
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

//imports xml converter library
import com.thoughtworks.xstream.io.xml.DomDriver;

public class DataConverter{

	private final List<Object> AllPersons = new ArrayList<Object>();
	private PersonsList PersonsList;
	private final List<Object> AllAssets = new ArrayList<Object>();
	private Assets Assets;
	
	
	public DataConverter(){
		this.PersonsList = new PersonsList();
		this.Assets = new Assets();
		loadPersons();
		loadAssets();
		jsonAssets(Assets);
		jsonPersons(PersonsList);
		xmlAssets(AllAssets);
		xmlPersons(AllPersons);
	}
	
	private void xmlPersons(List<Object> allPersons2) {
		
	}

	private void xmlAssets(List<Object> allAssets2) {
		
	}

	private void jsonPersons(com.iffi.PersonsList personsList2) {
		
	}

	private void jsonAssets(com.iffi.Assets assets2) {
		
	}

	/**
	 * 
	 * This is our Persons Parser. We load in the assets .csv file
	 * And returns an asset data file stored in json and xml format.
	 * 
	 * 
	 */
	private void loadPersons() {
	//opens a scanner with a try catch block 
	Scanner s = null;
	try {
		s = new Scanner(new File("Data/Persons.csv"));
	}
	catch (FileNotFoundException e) {
		throw new RuntimeException(e);
	}
	String firstLine = s.nextLine();
	String findTotal [] = firstLine.split(",");
	//reads the first line of csv file to count number of lines
	int count = Integer.parseInt(findTotal[0]);
	//iterates through the file tokenizing data for every line
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
			
			List<String> emails = new ArrayList<String>();
			for(int j = 8; j<tokens.length; j++) {
				String email = tokens[j];
				emails.add(email);
			}
			Address a = new Address(address,city,state,zip,country);
			Person p = new Person(personCode,lastName,firstName,a,emails);
			PersonsList.addPerson(p);
			AllPersons.add(p);
		
			
		}
		//closes the scanner
		s.close();
		
	}

		
		
	/**
	 * 
	 * This is our Assets Parser. We load in the assets .csv file
	 * And returns an asset data file stored in json and xml format.
	 * 
	 * 
	 */
	
	private void loadAssets() {
		Scanner s = null;
		try {
			s = new Scanner(new File("Data/Assets.csv"));
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		String firstLine = s.nextLine();
		String findTotal [] = firstLine.split(",");
		//reads the first line of csv file to count number of lines
		int count = Integer.parseInt(findTotal[0]);
		//iterates through the file tokenizing data for every line
		for(int i = 0; i < count; i++) {
			String line = s.nextLine();
			String tokens[] = line.split(",");
			//reads code of the asset to determine category of asset
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
			
			//throws an error if asset is not a S,C, or P
			else {
				throw new RuntimeException("Asset code invalid");
			}
		
		}
		//closes the scanner
		s.close();
		
	}

	//calls the DataConverter function to run the program 
	public static void main(String[] args) {
		DataConverter demo = new DataConverter();
	
    }


}











	
