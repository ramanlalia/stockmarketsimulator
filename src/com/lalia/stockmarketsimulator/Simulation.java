package com.lalia.stockmarketsimulator;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Simulation {
	private float cashValue;
	private List<Investment> investments;
	private List<ActionListener> actionListeners;	
	
	public Simulation(float cashValue) {
		this.cashValue = cashValue;
		this.investments = new ArrayList<Investment>();	
		this.actionListeners = new ArrayList<ActionListener>();
	}
	
	// Create a new simulation with the given amount of funds
	// This method retains the action listeners that have already been setup
	public void reinitialize(float cashValue) {
		this.cashValue = cashValue;
		this.investments = new ArrayList<Investment>();	
	}
	
	public void refreshPrices() {
		if(investments.isEmpty() == false) { 
			List<String> symbols = new ArrayList<String>();
			for(Investment investment: investments) symbols.add(investment.getSymbol());
			
			List<Float> stockPrices = StockMarket.getPrices(symbols);
			for(int i = 0; i < stockPrices.size(); i++) { 
				investments.get(i).setCurrentPrice(stockPrices.get(i));
			}
			
			invokeListeners();
		}
	}
		
	public void buyStock(String symbol, int quantity) {
		Investment newInvestment = new Investment(symbol, quantity);
		investments.add(newInvestment);
		cashValue -= newInvestment.getInitialValue();
		
		invokeListeners(); // Notify the GUI that something changed so it can update
	}
	
	public void sellStock(String symbol, int quantity) {
		Investment investmentToRemove = null;
		
		// Search for the stock by the given symbol 
		for(Investment investment: investments) {
			if(investment.getSymbol().equals(symbol)) {
				
				// If all shares of the stock are sold, remove the stock from the portfolio
				if(investment.getQuantity() <= quantity) {
					this.cashValue += investment.getValue();
					
					/* We have to remove the investment later because
					 * Java throws an exception when we try to remove
					 * it from within the loop.
					 */
					investmentToRemove = investment;
				}
				else {
					this.cashValue += investment.sell(quantity);
				}
				
				// Notify the GUI that something changed so it can update
				invokeListeners();
				
				break;
			}
		}
		
		if(investmentToRemove != null) investments.remove(investmentToRemove);
	}
	
	public void addListener(ActionListener actionListener) {
		actionListeners.add(actionListener);
	}
	
	public void invokeListeners() {
		for(ActionListener actionListener: actionListeners) actionListener.actionPerformed(null);
	}

	//////////////////////////////////
	// Accessor and Mutator Methods // 
	//////////////////////////////////
	
	public float getCashValue() { return cashValue; }
	
	public float getPortfolioValue() {
		float portfolioValue = 0f;		
		for(Investment investment: investments) portfolioValue += investment.getValue();		
		return portfolioValue;
	}
	
	public float getTotalValue() { return cashValue + getPortfolioValue(); }

	public List<Investment> getInvestments() { return investments; }

}
