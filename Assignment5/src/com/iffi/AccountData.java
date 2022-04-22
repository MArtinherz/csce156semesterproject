package com.iffi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class AccountData {

	/**
	 * Removes all records from all tables in the database.
	 */

	public static void clearDatabase() {
		DeleteRecords.RemoveRecord("delete from Address");
		DeleteRecords.RemoveRecord("delete from Email");
		DeleteRecords.RemoveRecord("delete from PurchasedAsset");
		DeleteRecords.RemoveRecord("delete from Asset");
		DeleteRecords.RemoveRecord("delete from `Account`");
		DeleteRecords.RemoveRecord("delete from Person");

	}

	/**
	 * Method to add a person record to the database with the provided data.
	 * 
	 * @param personCode
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String firstName, String lastName, String street, String city,
			String state, String zip, String country) {
		String personQuery = "insert into Person (personCode, lastName, firstName) values (?, ?, ?)";
		PreparedStatement ps = null;
		Person p = DatabaseLoader.loadPersonByPersonCode(personCode);
		if (p == null) {
			Connection conn = DatabaseCredentials.connection();
			try {
				ps = conn.prepareStatement(personQuery);
				ps.setString(1, personCode);
				ps.setString(2, lastName);
				ps.setString(3, firstName);
				ps.executeUpdate();
			} catch (SQLException e) {
				System.err.println("Can't insert Person in !");
				throw new RuntimeException(e);
			}
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("Couldn't close!");
				throw new RuntimeException(e);
			}

		} else {
			System.err.println("Person Already Exists In Database!");
			throw new RuntimeException();
		}
		AccountData.addAddress(personCode, street, city, state, zip, country);

	}

	/**
	 * Method that adds an address to table
	 * 
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */

	public static void addAddress(String personCode, String street, String city, String state, String zip,
			String country) {

		Address a = DatabaseLoader.loadAddressByCode(personCode);
		if (a == null) {
			String addressQuery = "insert into Address (personId, address, city, state, zip, country) values ( (select personId from Person where personCode = ?),?,?,?,?,?)";
			PreparedStatement ps = null;
			Connection conn = DatabaseCredentials.connection();
			try {
				ps = conn.prepareStatement(addressQuery);
				ps.setString(1, personCode);
				ps.setString(2, street);
				ps.setString(3, city);
				ps.setString(4, state);
				ps.setString(5, zip);
				ps.setString(6, country);
				ps.executeUpdate();

			} catch (SQLException e) {
				System.out.println("SQL Error, couldn't insert address!");
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("Couldn't close!");
				throw new RuntimeException(e);
			}

		} else {
			System.err.println("Address already exists for this person!");
			throw new RuntimeException();
		}
	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {

		String emailQuery = "insert into Email (personId, email) values ( (select personId from Person where personCode = ?), ?)";
		String Email = DatabaseLoader.loadEmailByEmail(email);
		PreparedStatement ps = null;
		if (Email == null) {
			Connection con = DatabaseCredentials.connection();
			try {
				ps = con.prepareStatement(emailQuery);
				ps.setString(1, personCode);
				ps.setString(2, email);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * Adds a crypto currency asset record to the database with the provided data.
	 * 
	 * @param assetCode
	 * @param label
	 * @param exchangeRate
	 * @param exchangeFeeRate
	 */
	public static void addCrypto(String assetCode, String label, double exchangeRate, double exchangeFeeRate) {
		String cryptoQuery = "insert into Asset(assetCode, label, currentPriceForOne, exchangeFeeRate, assetType) values (?,?,?,?,\"C\")";

		Asset result = DatabaseLoader.loadAssetByCode(assetCode);
		if (result == null) {
			Connection conn = DatabaseCredentials.connection();
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(cryptoQuery);
				ps.setString(1, assetCode);
				ps.setString(2, label);
				ps.setDouble(3, exchangeRate);
				ps.setDouble(4, exchangeRate);
				ps.executeUpdate();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				System.err.println("An unknown Error in insertion!");
				throw new RuntimeException(e);
			}
		} else {
			if (!(result instanceof Crypto)) {
				System.err.println("Wrong Asset Code! Watch what you're typing because this isn't Crypto!");
				throw new RuntimeException();
			} else {
				System.err.println("Crypto Asset already Exists");
				throw new RuntimeException();
			}
		}

	}

	/**
	 * Adds a property asset record to the database with the provided data.
	 * 
	 * @param assetCode
	 * @param label
	 * @param appraisedValue
	 */
	public static void addProperty(String assetCode, String label, double appraisedValue) {
		String propQuery = "insert into Asset (assetCode, label, currentPriceForOne, assetType) values (?, ?, ?, \"P\")";
		Asset result = DatabaseLoader.loadAssetByCode(assetCode);
		if (result == null) {
			Connection conn = DatabaseCredentials.connection();
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(propQuery);
				ps.setString(1, assetCode);
				ps.setString(2, label);
				ps.setDouble(3, appraisedValue);
				ps.executeUpdate();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				System.err.println("An error in insertion!");
				throw new RuntimeException(e);
			}

			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				System.err.println("Failure to Close!");
				throw new RuntimeException(e);
			}
		} else {
			if (!(result instanceof Property)) {
				System.err.println("Wrong Asset Code! Watch what you're typing because this isn't a Property!");
				throw new RuntimeException();
			} else {
				System.err.println("Property Asset already Exists");
				throw new RuntimeException();
			}
		}

	}

	/**
	 * Adds a stock asset record to the database with the provided data.
	 * 
	 * @param assetCode
	 * @param label
	 * @param stockSymbol
	 * @param sharePrice
	 */
	public static void addStock(String assetCode, String label, String stockSymbol, Double sharePrice) {
		String stockQuery = "insert into Asset (assetCode, label, symbol, currentPriceForOne, assetType) values (?, ?,?, ?, \"S\")";
		Asset result = DatabaseLoader.loadAssetByCode(assetCode);

		if (result == null) {
			Connection conn = DatabaseCredentials.connection();
			PreparedStatement ps = null;
			try {
				ps = conn.prepareStatement(stockQuery);
				ps.setString(1, assetCode);
				ps.setString(2, label);
				ps.setString(3, stockSymbol);
				ps.setDouble(4, sharePrice);
				ps.executeUpdate();
			} catch (SQLException e) {
				System.err.println("An  Error in insertion!");
				throw new RuntimeException(e);
			}
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				System.err.println("Failure to Close!");
				throw new RuntimeException(e);
			}
		}

		else {
			if (!(result instanceof Stock)) {
				System.err.println("Wrong Asset Code! Watch what you're typing because this isn't Stock!");
				throw new RuntimeException();
			} else {
				System.err.println("Stock Asset already Exists");
				throw new RuntimeException();
			}
		}

	}

	/**
	 * Adds an account record to the database with the given data. If the account
	 * has no beneficiary, the <code>beneficiaryCode</code> will be
	 * <code>null</code>. The <code>accountType</code> is expected to be either
	 * <code>"N"</code> (noob) or <code>"P"</code> (pro).
	 * 
	 * @param accountNumber
	 * @param accountType
	 * @param ownerCode
	 * @param managerCode
	 * @param beneficiaryCode
	 */
	public static void addAccount(String accountNumber, String accountType, String ownerCode, String managerCode,
			String beneficiaryCode) {

		String accountQuery = "insert into `Account` (accountCode, ownerId, accountType, managerId, beneId) "
				+ "values (?, (select personId from Person where personCode = ?),?, (select personId from Person where personCode = ?), "
				+ "(select personId from Person where personCode = ?))";

		PreparedStatement ps = null;
		Connection conn = DatabaseCredentials.connection();
		Account a = DatabaseLoader.loadAccountByAccountCode(accountNumber);

		if (a == null) {

			try {
				ps = conn.prepareStatement(accountQuery);
				ps.setString(1, accountNumber);
				ps.setString(2, ownerCode);
				ps.setString(3, accountType);
				ps.setString(4, managerCode);
				ps.setString(5, beneficiaryCode);

				ps.executeUpdate();

			} catch (SQLException e) {
				System.err.println("An  Error in insertion!");
				throw new RuntimeException(e);
			}
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				System.err.println("Failure to Close!");
				throw new RuntimeException(e);
			}
		} else {
			System.err.println("Account with that number already exists!");
			throw new RuntimeException();
		}

	}

	/**
	 * Adds a crypto currency asset corresponding to <code>assetCode</code> (which
	 * is assumed to already exist in the database) to the account corresponding to
	 * the provided <code>accountNumber</code>.
	 * 
	 * @param accountNumber
	 * @param assetCode
	 * @param purchaseDate
	 * @param purchaseExchangeRate
	 * @param numberOfCoins
	 */
	public static void addCryptoToAccount(String accountNumber, String assetCode, String purchaseDate,
			double purchaseExchangeRate, double numberOfCoins) {

		String addCryptoToA = "insert into PurchasedAsset(accountId, assetId, purchaseDate, PurchasedPriceForOne, TotalCoins) "
				+ "values ( (select accountId from `Account` where accountCode = ?), (select assetId from Asset where assetCode = ?), ?, ?, ?)";

		Account account = DatabaseLoader.loadAccountByAccountCode(accountNumber);
		Asset asset = DatabaseLoader.loadAssetByCode(assetCode);
		if (account == null) {
			System.err.println("Account doesn't exist!");
			throw new RuntimeException();
		}
		if (asset == null || !(asset instanceof Crypto)) {
			System.err.println("Improer Asset Code!");
			throw new RuntimeException();
		}

		PreparedStatement ps = null;
		Connection conn = DatabaseCredentials.connection();
		try {
			ps = conn.prepareStatement(addCryptoToA);
			ps.setString(1, accountNumber);
			ps.setString(2, assetCode);
			ps.setString(3, purchaseDate);
			ps.setDouble(4, purchaseExchangeRate);
			ps.setDouble(5, numberOfCoins);
			ps.executeUpdate();

		} catch (SQLException e) {
			System.err.println("Insertion Error!");
			throw new RuntimeException(e);
		}

		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Failure to Close!");
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds a property asset corresponding to <code>assetCode</code> (which is
	 * assumed to already exist in the database) to the account corresponding to the
	 * provided <code>accountNumber</code>.
	 * 
	 * @param accountNumber
	 * @param assetCodei
	 * @param purchaseDate
	 * @param purchasePrice
	 */
	public static void addPropertyToAccount(String accountNumber, String assetCode, String purchaseDate,
			double purchasePrice) {

		String propQuery = "insert into PurchasedAsset(accountId, assetId, purchaseDate,PurchasedPriceForOne) "
				+ "values ( (select accountId from `Account` where accountCode = ?), (select assetId from Asset where assetCode = ?),?, ?)";

		Account account = DatabaseLoader.loadAccountByAccountCode(accountNumber);
		Asset asset = DatabaseLoader.loadAssetByCode(assetCode);
		if (account == null) {
			System.err.println("Account doesn't exist!");
			throw new RuntimeException();
		}
		if (asset == null || !(asset instanceof Property)) {
			System.err.println("Improper Asset Code!");
			throw new RuntimeException();
		}

		PreparedStatement ps = null;
		Connection conn = DatabaseCredentials.connection();
		try {
			ps = conn.prepareStatement(propQuery);
			ps.setString(1, accountNumber);
			ps.setString(2, assetCode);
			ps.setString(3, purchaseDate);
			ps.setDouble(4, purchasePrice);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Insertion Error!");
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds a stock asset corresponding to <code>assetCode</code> (which is assumed
	 * to already exist in the database) to the account corresponding to the
	 * provided <code>accountNumber</code>.
	 * 
	 * @param accountNumber
	 * @param assetCode
	 * @param purchaseDate
	 * @param purchaseSharePrice
	 * @param numberOfShares
	 * @param dividendTotal
	 */
	public static void addStockToAccount(String accountNumber, String assetCode, String purchaseDate,
			double purchaseSharePrice, double numberOfShares, double dividendTotal) {

		String stockQuery = "insert into PurchasedAsset(accountId, assetId, purchaseDate,PurchasedPriceForOne, TotalShares, Dividend) "
				+ "values ( (select accountId from `Account` where accountCode = ?), (select assetId from Asset where assetCode = ?),?,?,?,?)";

		Account account = DatabaseLoader.loadAccountByAccountCode(accountNumber);
		Asset asset = DatabaseLoader.loadAssetByCode(assetCode);
		if (account == null) {
			System.err.println("Account doesn't exist!");
			throw new RuntimeException();
		}
		if (asset == null || !(asset instanceof Stock)) {
			System.err.println("Improer Asset Code!");
			throw new RuntimeException();
		}

		PreparedStatement ps = null;
		Connection conn = DatabaseCredentials.connection();
		try {
			ps = conn.prepareStatement(stockQuery);
			ps.setString(1, accountNumber);
			ps.setString(2, assetCode);
			ps.setString(3, purchaseDate);
			ps.setDouble(4, purchaseSharePrice);
			ps.setDouble(5, numberOfShares);
			ps.setDouble(6, dividendTotal);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Insertion Error!");
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds a stock option asset corresponding to <code>assetCode</code> (which is
	 * assumed to already exist in the database) to the account corresponding to the
	 * provided <code>accountNumber</code>.
	 * 
	 * @param accountNumber
	 * @param assetCode
	 * @param purchaseDate
	 * @param optionType
	 * @param strikeDate
	 * @param shareLimit
	 * @param premiumPerShare
	 * @param strikePricePerShare
	 */
	public static void addStockOptionToAccount(String accountNumber, String assetCode, String purchaseDate,
			String optionType, String strikeDate, double shareLimit, double premiumPerShare,
			double strikePricePerShare) {

		String optionQuery = "insert into PurchasedAsset(accountId,purchaseDate, assetId,PurchasedPriceForOne, strikeDate, strikePricePerShare, shareLimit, optionType) "
				+ "values ( (select accountId from `Account` where accountCode = ?),?, (select assetId from Asset where assetCode = ?),?,?,?,?,?)";

		Account account = DatabaseLoader.loadAccountByAccountCode(accountNumber);
		Asset asset = DatabaseLoader.loadAssetByCode(assetCode);
		if (account == null) {
			System.err.println("Account doesn't exist!");
			throw new RuntimeException();
		}
		if (asset == null || !(asset instanceof Stock)) {
			System.err.println("Improer Asset Code!");
			throw new RuntimeException();
		}

		PreparedStatement ps = null;
		Connection conn = DatabaseCredentials.connection();
		try {
			ps = conn.prepareStatement(optionQuery);
			ps.setString(1, accountNumber);
			ps.setString(2, purchaseDate);
			ps.setString(3, assetCode);
			ps.setDouble(4, premiumPerShare);
			ps.setString(5, strikeDate);
			ps.setDouble(6, strikePricePerShare);
			ps.setDouble(7, shareLimit);
			ps.setString(8, optionType);
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Insertion Error!");
			throw new RuntimeException(e);
		}

	}

}
