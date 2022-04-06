use mherz;

SET FOREIGN_KEY_CHECKS=0;
drop table if exists Address;
drop table if exists Email;
drop table if exists PurchasedAsset;
drop table if exists Asset;
drop table if exists `Account`;
drop table if exists Person;
SET FOREIGN_KEY_CHECKS=1;

-- Create a Person table since a lot relies on the Person
create table Person(
	personId int not null primary key auto_increment,
	personCode varchar(50) not null Unique,
	lastName varchar(50) not null,
    firstName varchar(50) not null

);
-- Create an Address table, connected back to a Person Instance, one-to-many
create table Address (
	addressId int not null primary key auto_increment,
    personId int not null Unique,
    address varchar(50) not null,
    city varchar(50) not null,
    state varchar(50) not null,
    zip varchar(50) not null,
    country varchar(50) not null,
    foreign key(personId) references Person(personId)
);

-- Create an Email table, connected to a person. One person can have multiple emails and also can have no no emails
-- One to many
create table Email(
	emailId int not null primary key auto_increment,
	personId int not null,
    email varchar(45) default null,
    foreign key(personId) references Person(personId)
);

-- Then create an Account table. Includes references to personId back to person along with beneficiary and manager?
create table `Account` (
	accountId int not null primary key auto_increment,
    accountCode varchar(50) not null Unique,
    ownerId int not null,
    accountType varchar(1) not null,
    managerId int not null,
    beneId int default null,
	foreign key(ownerId) references Person(personId),
    foreign key(managerId) references Person(personId),
    foreign key(beneId) references Person(personId)
);

-- Create an asset table with three distinct discrimators(stock, property and crypto) of an asset.alter
-- Longer since it includes original Asset information. Important note, person can have multiple purchases 
-- of the same asset but must be checked

create table Asset (
	assetId int not null primary key auto_increment,
    assetCode varchar(45) not null Unique,
    label varchar(55)  not null,
    currentPriceForOne double not null,
    assetType varchar(1) not null
);

-- Create a purchasedAsset table, representing one asset purhcased by an account. Many-to-many relationship

create table PurchasedAsset(
	PurchasedAssetId int not null primary key auto_increment,
	purchaseDate varchar(45) not null,
	accountId int not null,
    assetId int not null,
	PurchasedPriceForOne double not null,
	TotalCoins double default null,
    TotalShares double default null,
    exchangeFeeRate double default null,
    Dividend double default null,
    Symbol varchar(5) default null,	
    strikeDate varchar(45) default null,
    strikePricePerShare double default null,
    shareLimit int default null,
    optionType varchar(1) default null,
	foreign key(accountId) references `Account`(accountId),
    foreign key(assetId) references Asset(assetId)
);


-- Create our Test Data here

insert into Person (personCode, lastName, firstName) values ("A01F", "Jordan", "Michael");
insert into Person (personCode, lastName, firstName) values ("B02G", "Smith", "Katie");
insert into Person (personCode, lastName, firstName) values ("C03H","James","LeBron");
insert into Person (personCode, lastName, firstName) values ("D04J","Tebow","Katie");

insert into Address (personId, address, city, state, zip, country) values ( (select personId from Person where personCode = "A01F"),"1717 Goat Drive","Chicago","IL","60608","USA");
insert into Address (personId, address, city, state, zip, country) values ( (select personId from Person where personCode = "B02G"),"7935 Yankee Hill Road","Omaha","NE","68516","USA");
insert into Address (personId, address, city, state, zip, country) values ( (select personId from Person where personCode = "C03H"),"666 Flop Circle","Cleveland","OH","67561","USA");
insert into Address (personId, address, city, state, zip, country) values ( (select personId from Person where personCode = "D04J"),"4365 Mile Ct","Denver","CO","68791","USA");

insert into Email (personId, email) values ( (select personId from Person where personCode = "A01F"), "MJ@gmail.com");
insert into Email (personId, email) values ( (select personId from Person where personCode = "B02G"), "sarahsmith@gmail.com");
insert into Email (personId, email) values ( (select personId from Person where personCode = "B02G"), "sarahsmith@yahoo.com");
insert into Email (personId, email) values ( (select personId from Person where personCode = "C03H"), "LeJames@huskers.unl.edu");
insert into Email (personId) values ( (select personId from Person where personCode = "D04J"));

insert into `Account` (accountCode, ownerId, accountType, managerId, beneId) values ("AC012", 1,"P", 1, 1);
insert into `Account` (accountCode, ownerId, accountType, managerId, beneId) values ("AC013", 2,"N", 2, 2);
insert into `Account` (accountCode, ownerId, accountType, managerId, beneId) values ("AC014", 3,"P", 1, 3);
insert into `Account` (accountCode, ownerId, accountType, managerId) values ("AC015", 4,"N", 3);


insert into Asset (assetCode, label, currentPriceForOne, assetType) values ("S1", "Tesla", 860, "S");
insert into Asset (assetCode, label, currentPriceForOne, assetType) values ("S2", "Nike", 140.18, "S");
insert into Asset (assetCode, label, currentPriceForOne, assetType) values ("S3", "Wal-Mart", 135.33, "S");
insert into Asset (assetCode, label, currentPriceForOne, assetType) values ("S4", "T-Mobile", 124.48, "S");
insert into Asset (assetCode, label, currentPriceForOne, assetType) values ("C1", "Bitcoin", 42232.16, "C");
insert into Asset (assetCode, label, currentPriceForOne, assetType) values ("C2", "Dogecoin", .1447, "C");
insert into Asset (assetCode, label, currentPriceForOne, assetType) values ("C3", "Etherum", 2931.06, "C");
insert into Asset (assetCode, label, currentPriceForOne, assetType) values ("C4", "Litecoin", 124.37, "C");
insert into Asset (assetCode, label, currentPriceForOne, assetType) values ("P1", "Tim Tebow Card", 5, "P");
insert into Asset (assetCode, label, currentPriceForOne, assetType) values ("P2", "John Elway Rookie Card", 600, "P");
insert into Asset (assetCode, label, currentPriceForOne, assetType) values ("P3", "Tom Brady Rookie Card", 4000, "P");
insert into Asset (assetCode, label, currentPriceForOne, assetType) values ("P4", "Michael Jordan Rookie Card", 50000, "P");

-- Stock
insert into PurchasedAsset(accountId, assetId, purchaseDate,PurchasedPriceForOne, TotalShares, Dividend, Symbol) values (1,1,"2015-12-31",45,1000,1500.55,"TSLA");
insert into PurchasedAsset(accountId, assetId, purchaseDate,PurchasedPriceForOne, TotalShares, Dividend, Symbol) values (3,4,"2021-03-18",95,10,0,"TM");
-- Crypto
insert into PurchasedAsset(accountId, assetId,purchaseDate, PurchasedPriceForOne, TotalCoins, exchangeFeeRate) values (2,6,"2021-03-17",.6532,1000,2);
insert into PurchasedAsset(accountId, assetId,purchaseDate, PurchasedPriceForOne, TotalCoins, exchangeFeeRate) values (3,7,"2021-06-21", 4200.45, .11, 1.2);
-- Property
insert into PurchasedAsset(accountId, assetId, purchaseDate,PurchasedPriceForOne) values (1,9,"2010-10-05", 50000);

-- Option
insert into PurchasedAsset(accountId,purchaseDate, assetId,PurchasedPriceForOne, strikeDate, strikePricePerShare, shareLimit, optionType) values (2,"2021-03-18",2,1.50,"2021-05-01",120,100,"C");
insert into PurchasedAsset(accountId,purchaseDate, assetId,PurchasedPriceForOne, strikeDate, strikePricePerShare, shareLimit, optionType) values (4,"2020-03-08",1,15.35,"2022-05-17",700,200,"P");
insert into PurchasedAsset(accountId,purchaseDate, assetId,PurchasedPriceForOne, strikeDate, strikePricePerShare, shareLimit, optionType) values (4,"2020-03-08",1,30.55,"2022-04-29",1000,200,"C");
