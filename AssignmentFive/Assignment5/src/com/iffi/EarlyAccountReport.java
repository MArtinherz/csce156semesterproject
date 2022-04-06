package com.iffi;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import unl.cse.albums.Band;
import unl.cse.albums.DatabaseInfo;


/**
 * 
 * 
 * This is our AccountReport class, which reads through all four .csv files
 * And then outputs a summary report of all accounts and an individual report of each account's info along with stocks
 * 
 * @author Martin Herz, Michael Endacott
 *
 */


public class AccountReport{
	
	private final Map<Integer, Account> accounts = new HashMap<Integer, Account>();
	
	public AccountReport(){
		loadAccounts();
//		loadAssets();
//		loadAccounts();
//		summaryReports();
//		individualReports();
	}
	
	/**
	 * 
	 * 
	 * This is our Persons.csv parser. This loads in an csv of persons
	 * And outputs a json file of a person along with an xml file of information
	 * 
	 */
	
	private List<String> loadEmails(int personId) {
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseCredentials.URL, DatabaseCredentials.USERNAME, DatabaseCredentials.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		String EmailsQuery = "select email from Email where personId = ?";
		List<String> emails = new ArrayList<>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(EmailsQuery);
			ps.setInt(1, personId);
			rs = ps.executeQuery();
			while(rs.next()) {
				String email = rs.getString("email");
				emails.add(email);
			}
			ps.close();
			rs.close();
			
		} catch(SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return emails;
		//TODO: Set up connection to query, pull emails by id grabbed from load Persons, put into list
		
	}
	/**
	 * 
	 * @param personId
	 * @return An Address meant for loading the persons database that forms the Address call
	 */
	
	//TODO: Think about removing loadAddress through a join table with person to efficiently create it
	private Address loadAddress(int personId) {
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseCredentials.URL, DatabaseCredentials.USERNAME, DatabaseCredentials.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		String AddressQuery = "select address, city, state, zip, country from Address where personId = ?";
		Address address = null;
		
		//TODO: Set up the Address query by personID
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(AddressQuery);
			ps.setInt(1, personId);
			rs = ps.executeQuery();
			while(rs.next()) {
				String street = rs.getString("address");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String zip = rs.getString("zip");
				String country = rs.getString("country");
				address = new Address(street, city, state, zip, country);
			}
			rs.close();
			ps.close();
		}
		catch (SQLException e){
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
		
		//Return address class that we will build
		
		return address;
		
	}
	
	
	
	//TODO: Load persons from Persons table along with email and address of each person
	private Person loadPerson(int personId) {
		
		
		Connection conn = null;
		Person person = null;
		if(personId == 0) {
			return person;
		}
		
		try {
			conn = DriverManager.getConnection(DatabaseCredentials.URL, DatabaseCredentials.USERNAME, DatabaseCredentials.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
				
		String PersonsQuery = "select * from Person as P "
				+ "join Address on P.personId = Address.personId " +
				"where P.personId = ?";
		List<String> emails = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//TODO: Set up connection in a separate class


		//TODO: Retrieve Person
		try {
			ps = conn.prepareStatement(PersonsQuery);
			ps.setInt(1, personId);
			rs = ps.executeQuery();
			while (rs.next()) {
				int emailId = rs.getInt("personId");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String personCode = rs.getString("personCode");
				String street = rs.getString("address");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String zip = rs.getString("zip");
				String country = rs.getString("country");
				emails = this.loadEmails(emailId);
				Address address = new Address(street, city, state, zip, country);
				person = new Person(personCode, lastName, firstName, address, emails);
				System.out.println(person.toString());
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return person;
	}
	
	
	//TODO: Load the Account table in
	private void loadAccounts() {
		Connection conn = null;
		List<Asset> assets = null;
		
		try {
			conn = DriverManager.getConnection(DatabaseCredentials.URL, DatabaseCredentials.USERNAME, DatabaseCredentials.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		String AccountQuery = "select * from Account";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(AccountQuery);
			rs = ps.executeQuery();
			while(rs.next()) {
				//TODO: Add account variables here to load in, grab person information of owner, manager and beneficiary
				int ownerId = rs.getInt("ownerId");
				int accountId = rs.getInt("accountId");
				int managerId = rs.getInt("managerId");
				int beneficiaryId = rs.getInt("beneId");
				String accountCode = rs.getString("accountCode");
				String accountType = rs.getString("accountType");
				Person owner = loadPerson(ownerId);
				Person manager = loadPerson(managerId);
				Person beneficiary = loadPerson(beneficiaryId);
				Account account = new Account(owner,accountCode,accountType,manager,beneficiary);
				assets = this.loadAssets(accountId);
				//TODO: Add in our assets
				for(int i = 0; i < assets.size(); i++) {
					account.addAsset(assets.get(i));
				}
				
				//TODO: Add account to hashmap with accountId as key
				
			}
			ps.close();
			rs.close();
		} catch(SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
			
			
			
		}
		


	//TODO: Load Purchased Asset table, use join table to grab valuable generic asset information as well
	// Build our purchased Asset by first creating an account and then grabbing all assets assosciated with that account!
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	private List<Asset> loadAssets(int accountId) {
		List<Asset> AssetList = new ArrayList<>();
		
		String AssetQuery = "select * from PurchasedAsset as PA\r\n"
				+ "left join Asset on PA.assetId = Asset.assetId" +
				"where PA.accountId = ?";
		
		
		
		//TODO: Run this query, grab it's proper account from list and store into that account
		Connection conn = null;

		
		try {
			conn = DriverManager.getConnection(DatabaseCredentials.URL, DatabaseCredentials.USERNAME, DatabaseCredentials.PASSWORD);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(AssetQuery);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				//TODO: Build our proper asset based on types. Create generic build starting before if-else
				String assetType = rs.getString("assetType");
				String assetCode = rs.getString("assetCode");
				String label = rs.getString("label");
				double purchasedPriceForOne = rs.getDouble("PurchasedPriceForOne");
				LocalDate purchaseDate = LocalDate.parse(rs.getString("purchaseDate"));
								
				double currentPrice = rs.getInt("currentPriceForOne");
				if(assetType.equals("S")) {
					//TODO: Grab Stock, then separate into call, put or plain stock
					String optionType = rs.getString("optionType");
					if(optionType.equals("P")) {
						//TODO: Put Stuff here! and add to account!
					}
					else if(optionType.equals("C")) {
						//TODO: Call Stuff here!
					}
					else {
						//TODO: Build the stock
					}
					
					//TODO: Create necessary Stock info here

				}
				else if(assetType.equals("C")) {
					double exchangeFeeRate = rs.getDouble("exchangeFeeRate");
					Crypto c = new Crypto(assetCode, label, currentPrice, exchangeFeeRate);
					double TotalCoins = rs.getDouble("TotalCoins");
					Crypto crypto = new Crypto(c, purchasedPriceForOne, TotalCoins, purchaseDate);
					//TODO: Create the Crypto Class
				}
				
				else if (assetType.equals("P")){
					Property p = new Property(assetCode, label, currentPrice);
					Property property = new Property(p,purchaseDate, purchasedPriceForOne);
					//TODO: Create the property class
				}
				
				else {
					throw new SQLException("Asset Type is Missing!");
				}
				
				//TODO: Grab accountId, add to that account's list
				
			}
			rs.close();
			ps.close();
			
		} catch(SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return AssetList;
		
		
//		for(int i = 0; i < count; i++) {
//			String line = s.nextLine();
//			String tokens[] = line.split(",");
//			if(tokens[1].equals("P")){ //For property, stock and crypto
//				String code = tokens[0];
//				String label = tokens[2];
//				double appraisedValue = Double.parseDouble(tokens[3]);
//				Property p = new Property(code, label, appraisedValue);
//				assets.put(code, p);
//				
//
//
//			}
//			else if(tokens[1].equals("S")) {
//				String code = tokens[0];
//				String label = tokens[2];
//				String symbol = tokens[3];
//				double sharePrice = Double.parseDouble(tokens[4]);
//				Stock p = new Stock(code, label, symbol, sharePrice);
//				assets.put(code, p);
//
//
//			}
//			else if(tokens[1].equals("C")) {
//				String code = tokens[0];
//				String label = tokens[2];
//				double exchangeRate = Double.parseDouble(tokens[3]);
//				double exchangeFeeRate = Double.parseDouble(tokens[4]);
//				Crypto p = new Crypto(code, label, exchangeRate, exchangeFeeRate);
//				assets.put(code, p);
//
//			}
//			
//			
//			else {
//				throw new RuntimeException("Asset code invalid");
//			}
		
		
		
	}

//		
//	//TODO: Then, grab all assets related to each account and store those assets into that account class. Create a different class
//	//TODO: Run based on asset type, if stock break down to option and call
//	private void LoadPurchasedAsset() {
//		String PurchasedAssetQuery = "select * from PurchasedAsset where accountId = ?";
//		
//		//TODO: Load in Account from Map that stores account's ids. Pull Account and Account Key
//		
//		//TODO: Load in Ids from Asset that stores asset ids
//		
//	
//		//TODO: With that account class, add purchasedAssets of that account to the desired account
//	}
//		
//		
//	
//		
//
//
//	
//	//TODO: Return summary reports of an account. Use Class within account to produce summary report
//	
//	private void summaryReports() {
//		System.out.printf("Summary Report of each Client's total investment");
//		System.out.println("\n");
//		for(int i = 0; i<accounts.size();i++) {
//			Account account = accounts.get(i);
//			System.out.println(account.toString());
//		}
//	}
//	
//	/**
//	 * 
//	 * Individual reports of each account!!!. Will give personal info for each account
//	 * 
//	 */
//	
//	//TODO: return individual reports
//	private void individualReports() {
//		System.out.println("============================");
//		System.out.println("Individual Reports, assesing each client's investment and tracking its return");
//		for(int i = 0; i<accounts.size();i++) {
//			Account account = accounts.get(i);
//			System.out.println("\n");
//			System.out.println(account.toIndividualInfo());
//			for(int j = 0; j < account.getAssets().size(); j++) {
//				Asset asset = account.getAssets().get(j);
//				System.out.println("\n");
//				System.out.println(asset.toString());
//				System.out.println("\n");
//			}
//		}
	

	public static void main(String[] args) {
		AccountReport demo = new AccountReport();
    }
}
