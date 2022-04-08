package com.iffi;

import java.util.List;

public class Reports {
	
	public List<Account> accounts;
	
	
	public Reports(List<Account> accounts) {
		this.accounts = accounts;
		SummaryReports();
		IndividualReports();
	}
	
	/**
	 * 
	 * 
	 * Returns a summary report for each account in list of accounts
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

}
