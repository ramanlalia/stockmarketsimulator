package com.lalia.stockmarketsimulator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/* StockMarket Class
 * Interfaces with Yahoo's Finance API to provide real-world stock prices
 * and other stock information to other classes.
 * 
 * Author: Raman Lalia
 * Date: June 14, 2013
 * 
 * A reference and how-to for the API:
 * http://www.gummy-stuff.org/Yahoo-data.htm
 */
public class StockMarket {
	
	/* A DataType is the type of information that you want to access
	 * Example: the company's name or the current stock price
	 * 
	 * The associated "tags" correspond to Yahoo's Finance API
	 * For example:
	 * "n" -> Company Name
	 * "l1" -> Current Stock Price
	 * 
	 * More tags and information available at:
	 * http://www.gummy-stuff.org/Yahoo-data.htm
	 */
	private enum DataType {
		NAME("n"), 
		PRICE("l1");
		
		public String tag;		
		DataType(String tag) { this.tag = tag; }
	};
	
	// Checks if the requested stock symbol exists
	public static boolean exists(String symbol) {
		float price = Float.parseFloat(getYahooData(symbol, DataType.PRICE));
		return price != 0; // Yahoo returns 0 for the price if the stock symbol doesn't exist 
	}
	
	// Gets the company name associated with the stock symbol
	public static String getCompanyName(String symbol) {
		// Yahoo returns the company's name with quotes surrounding it (e.g. "Google Inc.")
		// We can remove these quotes with a call to replaceAll("\"", "")
		return getYahooData(symbol, DataType.NAME).replaceAll("\"", "").trim();
	}
	
	// Gets the price for a single company
	public static float getPrice(String symbol) {		
		String price = getYahooData(symbol, DataType.PRICE);
		return Float.parseFloat(price); // Convert String to float
	}	
	
	// Get the prices (as a list of floats) for multiple stocks 
	public static List<Float> getPrices(List<String> symbols) {
		// The following code converts the list to the format:
		// SYM1+SYM2+SYM3+SYM4
		// The list needs to be in this format for Yahoo's API
		String stocks = "";
		
		for(String symbol: symbols) {
			stocks += symbol;
			stocks += " ";
		}
		
		stocks = stocks.trim();
		stocks = stocks.replace(' ', '+');
		
		String pricesString = getYahooData(stocks, DataType.PRICE);
		String[] pricesArray = pricesString.split("\n");
		List<Float> pricesList = new ArrayList<Float>();
		
		for(String price: pricesArray) {
			pricesList.add(Float.parseFloat(price));
		}
		
		return pricesList;
	}
	
	// Gets the data requested for the specified symbols
	public static String getYahooData(String symbols, DataType data) {
		return retrieveURLContent("http://finance.yahoo.com/d/quotes.csv?s=" + symbols + "&f=" + data.tag);
	}
	
	// Retrieves the text content of the provided URL
	// Used to access Yahoo Finance information
	
	private static String retrieveURLContent(String url) {
		String urlContent = "";

		try {
			// Open a stream to the request URL
			URL urlToRetrieve = new URL(url);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlToRetrieve.openStream()));
			
			String line;
			
			// Read the content of the stream one line at a time
	        while((line = bufferedReader.readLine()) != null) // While there are more lines to read
	            urlContent += line + "\n";
	        
	        bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return urlContent;
	}
}
