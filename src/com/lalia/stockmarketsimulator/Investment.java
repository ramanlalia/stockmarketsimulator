package com.lalia.stockmarketsimulator;

public class Investment {
	private String symbol;
	private float paidPrice;
	
	private float currentPrice;
	
	private String companyName;
	private int quantity;
	
	// Create a new investment
	public Investment(String symbol, int quantity) {
		this.symbol = symbol;
		
		this.currentPrice = StockMarket.getPrice(symbol);
		this.paidPrice = this.currentPrice;
		
		this.companyName = StockMarket.getCompanyName(symbol);
		this.quantity = quantity;		
	}
	
	// Create an investment that was purchased previously
	public Investment(String symbol, String companyName, int quantity, float paidPrice) {
		this.symbol = symbol;
		this.companyName = companyName;
		this.paidPrice = paidPrice;
		this.quantity = quantity;
	}
	
	
	// Sell the specified quantity of stock
	// Returns the liberated funds
	public float sell(int quantity) {
		this.quantity -= quantity;
		return this.getCurrentPrice() * quantity;
	}
	
	////////////////////////////////
	// Accessor & Mutator Methods //
	////////////////////////////////
	
	public float getPaidPrice() { return paidPrice; }
	
	public float getCurrentPrice() { return currentPrice; }
	
	public void setCurrentPrice(float currentPrice) { this.currentPrice = currentPrice; }
	
	public float getChangeInPrice() { return new Float(getCurrentPrice() - getPaidPrice()); }
	
	public int getQuantity() { return quantity; }
	
	public float getInitialValue() {
		return quantity * paidPrice;
	}
	
	public float getValue() {
		return quantity * getCurrentPrice();
	}
	
	public float getProfitLoss() {
		return getValue() - getInitialValue();
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public String getCompanyName() {
		return companyName;
	}
}
