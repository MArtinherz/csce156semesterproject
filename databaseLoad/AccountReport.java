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
	
	
	public AccountReport(){
		loadAccounts();
//		summaryReports();
//		individualReports();
	}
	
	/**
	 * 
	 * 
	 * Takes in a personId from an private input
	 * 
	 * Returns: Emails based on personId from the SQL query
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
			
		} catch(SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		try {
			ps.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return emails;
		//TODO: Set up connection to query, pull emails by id grabbed from load Persons, put into list
		
	}

	
	//TODO: Think about removing loadAddress through a join table with person to efficiently create it

	
	/**
	 * 
	 * @param personId
	 * @return Person Info and Address Info of a given person from the Person and Address
	 */
	
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
				
		String PersonsQuery = "select P.personId as personId, P.personCode as personCode, P.lastName as lastName, P.firstName as firstName, A.city as city, A.country as country, A.address as address, A.state as state, A.zip as zip "
				+ "from Person as P "
				+ "join Address as A on P.personId = A.personId " +
				"where P.personId = ?";
		List<String> emails = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		//TODO: Set up connection in a separate class


		//TODO: Retrieve Person and Address
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
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			ps.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return person;
	}
	
	/**
	 * 
	 * @param None
	 * @return This returns all accounts along with their purchased assets through a SQL query.
	 * The query takes from the other 4 functions to create the account class
	 * 
	 */
	
	
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
		
		String AccountQuery = "select a.ownerId as ownerId, a.accountId as accountId, a.managerId as managerId, a.accountCode as accountCode, a.accountType as accountType, a.beneId as beneId"
				+ " from Account as a";
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
				
				for(int i = 0; i < assets.size(); i++) {
					account.addAsset(assets.get(i));
				}
				System.out.println(account.getAssets().size());
				
				
			}
			ps.close();
			rs.close();
		} catch(SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
			
			
			
		}
		


	
	/**
	 * 
	 * 
	 * @param The account id returned from the result of the account SQL query in table
	 * @return This returns a list of Assets owned by a particular account and then adds that account's asset to the class
	 * 
	 * 
	 */
	private List<Asset> loadAssets(int accountId) {
		List<Asset> AssetList = new ArrayList<>();
		
		String AssetQuery = "select PA.purchaseDate as purchaseDate, PA.PurchasedPriceForOne as PurchasedPriceForOne, TotalCoins, TotalShares, exchangeFeeRate, Dividend, Symbol, strikeDate, strikePricePerShare, shareLimit, optionType, A.assetCode as assetCode, A.label as label, A.currentPriceForOne as currentPriceForOne, A.assetType as assetType from PurchasedAsset as PA\r\n"
				+ "left join Asset as A on PA.assetId = A.assetId\r\n"
				+ "where PA.accountId = ?";
		
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
			ps.setInt(1, accountId);
			rs = ps.executeQuery();
			
			while(rs.next()) {

				String assetType = rs.getString("assetType");
				String assetCode = rs.getString("assetCode");
				String label = rs.getString("label");
				double purchasedPriceForOne = rs.getDouble("PurchasedPriceForOne");
				LocalDate purchaseDate = LocalDate.parse(rs.getString("purchaseDate"));
								
				double currentPrice = rs.getInt("currentPriceForOne");
				if(assetType.equals("S")) {
					String symbol = rs.getString("symbol");
					Stock s = new Stock(assetCode, label, symbol, currentPrice);
					
					//TODO: Grab generic Stock, then separate into call, put or plain stock
					String optionType = rs.getString("optionType");
					if("P".equals(optionType)) {
						LocalDate strikeDate = LocalDate.parse(rs.getString("strikeDate"));
						double strikePricePerShare = rs.getDouble("strikePricePerShare");
						int shareLimit = rs.getInt("shareLimit");
						//Put p = new Put(s, purchaseDate, strikePricePerShare, shareLimit, purchasedPriceForOne, strikeDate);
						//AssetList.add(p);

						//TODO: Put Stuff here! and add to account!
					}
					else if("C".equals(optionType)) {
						//TODO: Call Stuff here! will be same for either call or put
						LocalDate strikeDate = LocalDate.parse(rs.getString("strikeDate"));
						double strikePricePerShare = rs.getDouble("strikePricePerShare");
						int shareLimit = rs.getInt("shareLimit");
						
						//TODO: Call Stuff here! and add to account!
						
						//Call c = new Call(s, purchaseDate, strikePricePerShare, shareLimit, purchasedPriceForOne, strikeDate);
						//AssetList.add(c);
					}
					else{
						double TotalShare = rs.getDouble("TotalShares");
						double dividend = rs.getDouble("Dividend");
						Stock stock = new Stock(s, purchaseDate, purchasedPriceForOne, TotalShare, dividend);
						AssetList.add(stock);
					}
					

				}
				else if(assetType.equals("C")) {
					double exchangeFeeRate = rs.getDouble("exchangeFeeRate");
					Crypto c = new Crypto(assetCode, label, currentPrice, exchangeFeeRate);
					double TotalCoins = rs.getDouble("TotalCoins");
					Crypto crypto = new Crypto(c, purchasedPriceForOne, TotalCoins, purchaseDate);
					AssetList.add(crypto);

				}
				
				else if (assetType.equals("P")){
					Property p = new Property(assetCode, label, currentPrice);
					Property property = new Property(p,purchaseDate, purchasedPriceForOne);
					AssetList.add(property);

				}
				
				else {
					throw new SQLException("Asset Type is Missing or not in database!");
				}
				
				
			}
			rs.close();
			ps.close();
			
		} catch(SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			ps.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return AssetList;
		
		
		
		
	}

	

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
