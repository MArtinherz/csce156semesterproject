package com.iffi;


import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 
 * 
 * @author Martin Herz, Michael Endacott
 * 
 * This is our database reader, reads from our database and sets up the table back into our classes
 *
 */


public class AccountReport {
	
	private final Map<String, Person> persons = new HashMap<String, Person>();
	private final Map<String, Asset> assets = new HashMap<String, Asset>();
	private final List<Account> accounts = new ArrayList();
	private final List<Asset> genericassets = new ArrayList();
	
	public AccountReport(){
		loadPersons();
		loadAssets();
		loadAccounts();
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
			List<String> emails = new ArrayList<String>();
			for(int j = 8; j<tokens.length; j++) {
				String email = tokens[j];
				emails.add(email);
			}
			Address a = new Address(address, city, state, zip, country);
			Person p = new Person(personCode,lastName,firstName,a, emails);
			persons.put(personCode, p);

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
				assets.put(code, p);
				
				genericassets.add(p);
 
			}
			else if(tokens[1].equals("S")) {
				String code = tokens[0];
				String label = tokens[2];
				String symbol = tokens[3];
				double sharePrice = Double.parseDouble(tokens[4]);
				Stock p = new Stock(code, label, sharePrice);
				assets.put(code, p);
				genericassets.add(p);
 
			}
			else if(tokens[1].equals("C")) {
				String code = tokens[0];
				String label = tokens[2];
				double exchangeRate = Double.parseDouble(tokens[3]);
				double exchangeFeeRate = Double.parseDouble(tokens[4]);
				Crypto p = new Crypto(code, label, exchangeRate, exchangeFeeRate);
				assets.put(code, p);
				genericassets.add(p);
			}
			
			
			else {
				throw new RuntimeException("Asset code invalid");
			}
		}
		
		
	}
	private void loadAccounts() {
		Scanner s = null;
		try {
			s = new Scanner(new File("data/Accounts.csv"));
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		String firstLine = s.nextLine();
		String findTotal [] = firstLine.split(",");
		int count = Integer.parseInt(findTotal[0]);
		
		for(int i = 0; i < count; i++) {
			String line = s.nextLine().replace(" ", "");
			String tokens[] = line.split(",");
			String acctNumber = tokens[0];
			String accountType = tokens[1];
			String owner = tokens[2];
			String manager = tokens[3];
			String beneficiaryCode = tokens[4];
			Person person = persons.get(owner);
			Account account = new Account(person, acctNumber, accountType, owner, manager, beneficiaryCode);
			
			for(int j = 5; j<tokens.length; j++) {
				String iD = tokens[j];
				if(assets.containsKey(iD)) {
					if(assets.get(iD) instanceof Stock) {
						Stock stock = (Stock) assets.get(iD);
						if(tokens[j + 1].equals("P")) {
							Option put = new Put(stock, tokens[j + 1], LocalDate.parse(tokens[j + 2]),
									Double.parseDouble(tokens[j + 3]), Integer.parseInt(tokens[j + 4]), Double.parseDouble(tokens[j + 5]), LocalDate.parse(tokens[j + 6]));
							account.addAsset(put);
//							System.out.println(put.toString());
						}
						else if(tokens[j + 1].equals("C")) {
							Option call = new Call(stock, tokens[j + 1], LocalDate.parse(tokens[j + 2]),
									Double.parseDouble(tokens[j + 3]), Integer.parseInt(tokens[j + 4]), Double.parseDouble(tokens[j + 5]), LocalDate.parse(tokens[j + 6]));
							account.addAsset(call);
//							System.out.println(call.toString());
						}
						else if(tokens[j + 1].equals("S")) {
							Stock stockUpd = new Stock(stock, LocalDate.parse(tokens[j + 2]), Double.parseDouble(tokens[j + 3]), Integer.parseInt(tokens[j + 4]), Double.parseDouble(tokens[j + 5]) );
							account.addAsset(stockUpd);
//							System.out.println(stockUpd.toString());
							
						}
					}
					
						
					
					else if(assets.get(iD) instanceof Crypto){
						Crypto c = (Crypto) assets.get(iD);
						Crypto crypto = new Crypto(c, Double.parseDouble(tokens[j + 2]), Double.parseDouble(tokens[j + 3]), LocalDate.parse(tokens[j + 1]) );
						account.addAsset(crypto);
					}
					else if(assets.get(iD) instanceof Property){
						Property p = (Property) assets.get(iD);
						Property prop = new Property(p, LocalDate.parse(tokens[j + 1]), Double.parseDouble(tokens[j + 2]));
						account.addAsset(prop);
					}
					else {
					;
				}
				}
				
			}
			accounts.add(account);
			
	}
 
	}

	


	public static void main(String[] args) {
		DatabaseLoader demo = new DatabaseLoader();
		Reports report = new Reports(demo.getAccounts());

    }


}


