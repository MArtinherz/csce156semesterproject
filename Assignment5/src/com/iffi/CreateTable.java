package com.iffi;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
	
	public static void CreateTables() {
		Connection con = DatabaseCredentials.connection();
		
		String creationPersonQuery = "create table Person(\r\n"
				+ "	personId int not null primary key auto_increment,\r\n"
				+ "	personCode varchar(50) not null Unique,\r\n"
				+ "	lastName varchar(50) not null,\r\n"
				+ "firstName varchar(50) not null\r\n"
				+ "\r\n"
				+ ")";
		String emailCreation = "create table Email(\r\n"
				+ "	emailId int not null primary key auto_increment,\r\n"
				+ "	personId int not null,\r\n"
				+ "email varchar(45) default null,\r\n"
				+ "foreign key(personId) references Person(personId)\r\n"
				+ ")";
		String AddressCreation = "create table Address (\r\n"
				+ "	addressId int not null primary key auto_increment,\r\n"
				+ "personId int not null Unique,\r\n"
				+ "address varchar(50) not null,\r\n"
				+ "city varchar(50) not null,\r\n"
				+ "state varchar(50) not null,\r\n"
				+ "zip varchar(50) not null,\r\n"
				+ "country varchar(50) not null,\r\n"
				+ "foreign key(personId) references Person(personId)\r\n"
				+ ")";
		String AccountCreation = "create table `Account` (\r\n"
				+ "	accountId int not null primary key auto_increment,\r\n"
				+ "    accountCode varchar(50) not null Unique,\r\n"
				+ "    ownerId int not null,\r\n"
				+ "    accountType varchar(1) not null,\r\n"
				+ "    managerId int not null,\r\n"
				+ "    beneId int default null,\r\n"
				+ "	foreign key(ownerId) references Person(personId),\r\n"
				+ "    foreign key(managerId) references Person(personId),\r\n"
				+ "    foreign key(beneId) references Person(personId)\r\n"
				+ ")";
		String AssetCreation = "create table Asset (\r\n"
				+ "	assetId int not null primary key auto_increment,\r\n"
				+ "    assetCode varchar(45) not null Unique,\r\n"
				+ "    label varchar(55)  not null,\r\n"
				+ "    currentPriceForOne double not null,\r\n"
				+ "    assetType varchar(1) not null,\r\n"
				+ "    exchangeFeeRate double default null\r\n"
				+ "    symbol varchar(5) default null\r\n"
				+ ")";
		String PACreation = "create table PurchasedAsset(\r\n"
				+ "	PurchasedAssetId int not null primary key auto_increment,\r\n"
				+ "	purchaseDate varchar(45) not null,\r\n"
				+ "	accountId int not null,\r\n"
				+ "    assetId int not null,\r\n"
				+ "	   PurchasedPriceForOne double not null,\r\n"
				+ "	   TotalCoins double default null,\r\n"
				+ "    TotalShares double default null,\r\n"
				+ "    Dividend double default null,\r\n"
				+ "    Symbol varchar(5) default null,	\r\n"
				+ "    strikeDate varchar(45) default null,\r\n"
				+ "    strikePricePerShare double default null,\r\n"
				+ "    shareLimit int default null,\r\n"
				+ "    optionType varchar(1) default null,\r\n"
				+ "	   foreign key(accountId) references `Account`(accountId),\r\n"
				+ "    foreign key(assetId) references Asset(assetId)\r\n"
				+ ")";
		
		Statement state = null;
		
		try {
			state = con.createStatement();
			state.addBatch(creationPersonQuery);
			state.addBatch(emailCreation);
			state.addBatch(AddressCreation);
			state.addBatch(AccountCreation);
			state.addBatch(AssetCreation);
//			state.addBatch(PACreation);
			state.executeBatch();
			System.out.println("Database is Set-Up!");

		}
		catch(SQLException e) {
			System.out.println("SQL Error, Table Creation Error!");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		try {
			state.close();
		}
		catch (SQLException e){
			System.out.println("Couldn't close!");
			throw new RuntimeException(e);
		}
	}

}
