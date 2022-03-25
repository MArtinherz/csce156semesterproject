-- Assignent Four Queries


drop table if exists Properties;
drop table if exists TotalAssets;

-- Query 1, grab main person attributes(first name, lastname and personcode)
select personCode, lastName, firstName from Person;


-- Query Two, grab major fields of every person except their email(includes address of person)
-- Create a join table with Address
select * 
from Person
join Address on Address.personId = Person.personId;

-- Query Three, grab emails of a specific person. Use person first and last name in this case to find an email
select email
from Email
join Person on Person.personId = Email.personId
where Person.lastName = "Smith" and Person.firstName = "Katie";

-- Query Four, change emails of a specific person. Update email since this person originally had none.
-- This changes an already existing column and doesn't add a new email column
update Email
set email = "katietebow@tebowrulesall.com"
where Email.personId = 4;


-- Query Six, Get all assets of an account. Use union operator to combine Option and Asset table for a specific account
select assetId, assetType from Asset where Asset.accountId = 1
Union
select optionId, assetType from `Option` as o where o.accountId = 1;

-- Query Seven A query to find the total number of accounts owned by each person
select Person.personId, count(`Account`.accountId) as TotalAccounts
from Person
left join `Account` on `Account`.ownerId = Person.personId
group by Person.personId;

-- Query Eight A query to find the total number of accounts managed by each person
select a.managerId as ManagerId, count(a.accountId) as TotalAccounts
from `Account` as a
left join Person on Person.personId = a.managerId
group by a.managerId;


-- Query 9 A query to find the total number of assets on each account
create table TotalAssets
select accountId, assetId, assetType from Asset
Union
select accountId, optionId, assetType from `Option` as o;

select ta.accountId, count(ta.assetId) as NumberOfAssets
from TotalAssets as ta
group by ta.accountId;

-- Query 10 . A query to find the total value of all stock assets on a particular account (hint: you can take an aggregate of a mathematical expression).
select (Asset.TotalShares * Asset.currentPriceForOne) + Asset.Dividend as `Value`, Asset.symbol
from Asset
where Asset.assetType = "S" and Asset.accountId = 1;

-- Query 11 A query to find the total value of all stock assets on all accounts.
select Asset.accountId, sum((Asset.TotalShares * Asset.currentPriceForOne) + Asset.Dividend) as `Value`
from Asset
where Asset.assetType = "S"
group by Asset.accountId;


-- Query 12 A query to detect duplicate property assets in a particular account 
create table Properties
select * from Asset
where AssetType = "Pr";

select P.accountId, P.assetCode, count(P.assetCode) as TotalProps
from Properties as P
where P.accountId = 1
group by P.assetCode;


-- Query 13 Write a query to detect a potential instances of fraud for option assets: the purchase
-- date should always be before the strike date. Report any instances where this is not
-- the case.
select accountId, Date(o.purchaseDate) as purchaseDate, Date(o.strikeDate) as OptionDate, "Fraud"
from `Option` as o
where Date(o.purchaseDate) > Date(o.strikeDate);

-- Query Five, A query or series of queries to remove a specific person record(must delete all assosciations with persons)
(select accountId from `Account` where ownerId = 3);


delete from `Option` where accountId in (3,4); 
delete from Asset where accountId in (3,4);
delete from Email where personId = 3;
delete from Address where personId = 3;
delete from `Account` where ownerId = 3;
delete from Person where personId = 3;