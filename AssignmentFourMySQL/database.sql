
drop table if exists Address;
drop table if exists Email;
drop table if exists `Option`;
drop table if exists Asset;
drop table if exists `Account`;
drop table if exists Person;

-- Create a Person table since a lot relies on the Person
create table Person(
	personId int not null primary key auto_increment,
	personCode varchar(50) not null Unique,
	lastName varchar(50) not null,
    firstName varchar(50) not null

);


-- Create an Address table, connected back to a Person Instance
create table Address (
	addressId int not null primary key auto_increment,
    personId int not null,
    Address varchar(50) not null,
    city varchar(50) not null,
    state varchar(50) not null,
    zip varchar(50) not null,
    country varchar(50) not null,
    foreign key(personId) references Person(personId)
	

);

-- Create an Email table, connected to a person. One person can have multiple emails and also can have no no emails
create table Email(
	emailId int not null primary key auto_increment,
	personId int not null,
    email varchar(45) default null,
    foreign key(personId) references Person(personId)
);

-- Then create an Account table. Includes references to personId back to person along with beneficiary and manager?
create table `Account` (
	accountId int not null primary key auto_increment,
    accountCode varchar(50) not null,
    ownerId int not null,
    accountType varchar(1) not null,
    managerId int not null,
    benecodeId int default null,
	foreign key(ownerId) references Person(personId),
    foreign key(managerId) references Person(personId),
    foreign key(benecodeId) references Person(personId)
);

-- Create an asset table with three distinct discrimators(stock, property and crypto) of an asset.alter
-- Longer since it includes original Asset information. Important note, person can have multiple purchases 
-- of the same asset but must be checked

create table Asset (
	assetId int not null primary key auto_increment,
    assetCode varchar(45) not null,
    label varchar(55)  not null,
    currentPriceForOne double not null,
    PurchasePriceForOne double not null,
    purchaseDate varchar(45) not null,
    assetType varchar(2) not null,
    accountId int not null,
    TotalCoins double default null,
    TotalShares double default null,
    exchangeFeeRate double default null,
    Dividend double default null,
    Symbol varchar(5) default null,
    foreign key(accountId) references `Account`(accountId)

);

-- Create an option table since this was its own class with two subclasses. Option has two types, Put and Call, along with information about underlying stock
desc Asset;

create table `Option`(
	optionId int not null primary key auto_increment,
    assetType varchar(1) not null,
    currentPriceForOne double not null,
    symbol varchar(5) not null,
    purchaseDate varchar(45) not null,
    strikeDate varchar(45) not null,
    strikePricePerShare double not null,
    premiumPerShare double not null,
    shareLimit int not null,
    accountId int not null,
    foreign key(accountId) references `Account`(accountId)
);
-- Create our Test Data here

insert into Person (personCode, lastName, firstName) values ("A01F", "Jordan", "Michael");
insert into Person (personCode, lastName, firstName) values ("B02G", "Smith", "Katie");
insert into Person (personCode, lastName, firstName) values ("C03H","James","LeBron");
insert into Person (personCode, lastName, firstName) values ("D04J","Tebow","Katie");

insert into Address (personId, Address, city, state, zip, country) values ( (select personId from Person where personCode = "A01F"),"1717 Goat Drive","Chicago","IL","60608","USA");
insert into Address (personId, Address, city, state, zip, country) values ( (select personId from Person where personCode = "B02G"),"7935 Yankee Hill Road","Omaha","NE","68516","USA");
insert into Address (personId, Address, city, state, zip, country) values ( (select personId from Person where personCode = "C03H"),"666 Flop Circle","Cleveland","OH","67561","USA");
insert into Address (personId, Address, city, state, zip, country) values ( (select personId from Person where personCode = "D04J"),"4365 Mile Ct","Denver","CO","68791","USA");

insert into Email (personId, email) values ( (select personId from Person where personCode = "A01F"), "MJ@gmail.com");
insert into Email (personId, email) values ( (select personId from Person where personCode = "B02G"), "sarahsmith@gmail.com");
insert into Email (personId, email) values ( (select personId from Person where personCode = "B02G"), "sarahsmith@yahoo.com");
insert into Email (personId, email) values ( (select personId from Person where personCode = "C03H"), "LeJames@huskers.unl.edu");
insert into Email (personId) values ( (select personId from Person where personCode = "D04J"));

insert into `Account` (accountCode, ownerId, accountType, managerId, benecodeId) values ("AC012", 1,"P", 1, 1);
insert into `Account` (accountCode, ownerId, accountType, managerId, benecodeId) values ("AC013", 2,"N", 2, 2);
insert into `Account` (accountCode, ownerId, accountType, managerId, benecodeId) values ("AC014", 3,"P", 1, 3);
insert into `Account` (accountCode, ownerId, accountType, managerId) values ("AC015", 3,"N", 3);
insert into `Account` (accountCode, ownerId, accountType, managerId) values ("AC016", 4,"N", 4);


insert into Asset (accountId, assetCode, label, currentPriceForOne, PurchasePriceForOne, assetType, purchaseDate, TotalShares, Dividend, Symbol) values (1, "STK40", "Tesla", 860, 200,"S", "2017-10-22", 300, 500, "TSLA");
insert into Asset (accountId, assetCode, label, currentPriceForOne, PurchasePriceForOne, assetType, purchaseDate) values (1, "CA22", "Tebow Card", 5, 50000,"Pr", "2010-05-17");
insert into Asset (accountId, assetCode, label, currentPriceForOne, PurchasePriceForOne, assetType, purchaseDate) values (1, "CA22", "Tebow Card", 5, 50000,"Pr", "2010-05-17");
insert into Asset (accountId, assetCode, label, currentPriceForOne, PurchasePriceForOne, assetType, purchaseDate) values (1, "CA23", "Deez Card", 5000, 500,"Pr", "2020-05-17");

insert into Asset (accountId, assetCode, label, currentPriceForOne, PurchasePriceForOne, assetType, purchaseDate, TotalCoins, exchangeFeeRate ) values (2,"CRPT00", "Etherium", 3060.52, 4000.50, "C", "2021-11-21", 1.3, .01);
insert into Asset (accountId, assetCode, label, currentPriceForOne, PurchasePriceForOne, assetType, purchaseDate, TotalCoins, exchangeFeeRate) values (3,"CRPT01", "Dogecoin", .15, .70, "C", "2021-04-17", 5000, .02);
insert into Asset (accountId, assetCode, label, currentPriceForOne, PurchasePriceForOne, assetType, purchaseDate, TotalShares, Dividend, Symbol) values (3,"STK41", "Nike", 145, 60,"S", "2018-10-22", 40, 214.34, "NIKE");
insert into Asset (accountId, assetCode, label, currentPriceForOne, PurchasePriceForOne, assetType, purchaseDate, TotalShares, Dividend, Symbol) values (1, "STK42", "Wal-Mart", 250, 120,"S", "2017-10-22", 500, 290, "WAL");



insert into `Option` (accountId, assetType, purchaseDate, strikeDate, strikePricePerShare, premiumPerShare, shareLimit, currentPriceForOne, Symbol) values (2, "C", "2021-11-10", "2022-11-10", 125.50, 3.50, 125, 145, "NIKE");
insert into `Option` (accountId, assetType, purchaseDate, strikeDate, strikePricePerShare, premiumPerShare, shareLimit, currentPriceForOne, Symbol) values (4, "P", "2021-09-12", "2022-05-17", 705.50, 13.65, 263, 860, "TSLA");
insert into `Option` (accountId, assetType, purchaseDate, strikeDate, strikePricePerShare, premiumPerShare, shareLimit, currentPriceForOne, Symbol) values (4, "C", "2022-03-10", "2022-03-31", 830, 20.50, 100, 860, "TSLA");

insert into `Option` (accountId, assetType, purchaseDate, strikeDate, strikePricePerShare, premiumPerShare, shareLimit, currentPriceForOne, Symbol) values (2, "C", "2022-03-31", "2022-03-24", 800, 15.50, 300, 860, "TSLA");


