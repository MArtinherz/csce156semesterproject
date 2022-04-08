package com.iffi;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author Martin Herz, Michael Endacott
 * 
 * This is our database reader, reads from our database and sets up the table back into our classes
 *
 */


public class DatabaseLoader {
	
	private List<Account> accounts = new ArrayList<Account>();

	
	
	public DatabaseLoader(){
		loadAccounts();
		SummaryReports();
		IndividualReports();
	}
	
	/**
	 * 
	 * @param personId
	 * @return the emails of that person from the emails table in database, may be multiple emails so returns list
	 */
	
	private List<String> loadEmails(int personId) {
		
		Connection conn = DatabaseCredentials.connection();

		
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
			e.printStackTrace();
		}		
		return emails;
		
	}

	

	
	/**
	 * 
	 * @param personId retrieved from Account table
	 * @return Person Info and Address Info of a given person from the Person and Address
	 */
	
	private Person loadPerson(int personId) {
		
		
		Connection conn = DatabaseCredentials.connection();
		Person person = null;
		if(personId == 0) {
			return person;
		}
		

				
		String PersonsQuery = "select P.personId as personId, P.personCode as personCode, P.lastName as lastName, P.firstName as firstName, A.city as city, A.country as country, A.address as address, A.state as state, A.zip as zip "
				+ "from Person as P "
				+ "join Address as A on P.personId = A.personId " +
				"where P.personId = ?";
		List<String> emails = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		


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
			e.printStackTrace();
		}	
		return person;
	}
	
	/**
	 * 
	 * @param None
	 * @return This returns all accounts along with their purchased assets through a SQL query.
	 * The query takes from the other 4 functions to create the account class, which needs information from rest
	 * 
	 */
	
	
	private void loadAccounts() {
		Connection conn = DatabaseCredentials.connection();
		List<Asset> assets = null;

		
		String AccountQuery = "select a.ownerId as ownerId, a.accountId as accountId, a.managerId as managerId, a.accountCode as accountCode, a.accountType as accountType, a.beneId as beneId"
				+ " from Account as a";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(AccountQuery);
			rs = ps.executeQuery();
			while(rs.next()) {
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
				accounts.add(account);

				
				
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
	 * @return This returns a list of Assets owned by a particular account and then adds that to account's assets list
	 * 
	 * 
	 */
	private List<Asset> loadAssets(int accountId) {
		List<Asset> AssetList = new ArrayList<>();
		
		String AssetQuery = "select PA.purchaseDate as purchaseDate, PA.PurchasedPriceForOne as PurchasedPriceForOne, TotalCoins, TotalShares, exchangeFeeRate, Dividend, Symbol, strikeDate, strikePricePerShare, shareLimit, optionType, A.assetCode as assetCode, A.label as label, A.currentPriceForOne as currentPriceForOne, A.assetType as assetType from PurchasedAsset as PA\r\n"
				+ "left join Asset as A on PA.assetId = A.assetId\r\n"
				+ "where PA.accountId = ?";
		
		Connection conn = DatabaseCredentials.connection();

		
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
								
				double currentPrice = rs.getDouble("currentPriceForOne");
				
				if(assetType.equals("S")) {
					String symbol = rs.getString("symbol");
					Stock s = new Stock(assetCode, label, currentPrice);
					String optionType = rs.getString("optionType");
					if("P".equals(optionType)) {
						LocalDate strikeDate = LocalDate.parse(rs.getString("strikeDate"));
						double strikePricePerShare = rs.getDouble("strikePricePerShare");
						int shareLimit = rs.getInt("shareLimit");
						Put p = new Put(s, purchaseDate, strikePricePerShare, shareLimit, purchasedPriceForOne, strikeDate);
						AssetList.add(p);

					}
					else if("C".equals(optionType)) {
						LocalDate strikeDate = LocalDate.parse(rs.getString("strikeDate"));
						double strikePricePerShare = rs.getDouble("strikePricePerShare");
						int shareLimit = rs.getInt("shareLimit");												
						Call c = new Call(s, purchaseDate, strikePricePerShare, shareLimit, purchasedPriceForOne, strikeDate);
						AssetList.add(c);


					}
					else{
						double TotalShare = rs.getDouble("TotalShares");
						double dividend = rs.getDouble("Dividend");
						Stock stock = new Stock(s, purchaseDate, symbol, purchasedPriceForOne, TotalShare, dividend);
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
	
	
	/**
	 * 
	 * @return Summary Reports of account along with totals in fees, value and gain of all accounts
	 */
	
	private void SummaryReports() {
		double totalFees = 0.0;
		double totalValue = 0.0;
		double totalGain = 0.0;
		for(Account account : accounts) {
			totalFees += account.FeeCalc();
			totalValue += account.getValue();
			totalGain += account.getGain();
			System.out.println(account.toString());
		}
		System.out.printf("\t\t\t Fees: %.2f Total Gain: %.2f \t Total Value: %.2f", totalFees, totalValue, totalGain);
		System.out.println("\n");
	}
	
	/**
	 * 
	 * @return returns details info of each account from person to asset and totals
	 * 
	 */
	
	private void IndividualReports() {
		for(Account account : accounts) {
			System.out.println(account.toIndividualInfo());
			System.out.println(account.AssetInformation());
			System.out.println(account.totalInfo());
		}
	}

	public static void main(String[] args) {
		DatabaseLoader demo = new DatabaseLoader();
    }


}


