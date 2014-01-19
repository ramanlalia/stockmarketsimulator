package com.lalia.stockmarketsimulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import com.lalia.stockmarketsimulator.gui.MainFrame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class StockMarketSimulator {	
	private static Simulation currentSimulation;
	private static MainFrame mainFrame;
	
	// TODO: implement custom sorter

	public static void main(String[] args) {
		currentSimulation = new Simulation(500000f);
		
		try {		    
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // "Nimbus" LAF not available
		}
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	mainFrame = new MainFrame();
            	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	mainFrame.setVisible(true);
            }
        });
		
		// Refresh stock prices every 30 seconds (30 * 1000 milliseconds)
		new Timer().scheduleAtFixedRate(new TimerTask() {			
			@Override
			public void run() {
				getCurrentSimulation().refreshPrices();
			}
		}, 0, 30 * 1000);
	}
	
	// A simulation file has the following format:
	// [Cash Value]
	// [Stock #1 Symbol]
	// [Stock #1 Quantity]
	// [Stock #1 Price Paid]
	// [Stock #2 Symbol]
	// [Stock #2 Quantity]
	// [Stock #2 Price Paid]
	// ...
	public static void readSimulationFromFile(File file) {
		Scanner s = null;
		
		try {
			s = new Scanner(file);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(mainFrame, "The file you requested was not found.", "File Not Found", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// The first line of the file contains the cash value of the simulation
		currentSimulation.reinitialize(Float.parseFloat(s.nextLine()));		
		addStock(s);		
		s.close();
		
		currentSimulation.refreshPrices();
	}
	
	// This method recursively adds stocks from a file to the simulation
	private static void addStock(Scanner s) {
		// If there are more investments in the file
		if(s.hasNextLine()) {
			String symbol = s.nextLine();
			String companyName = s.nextLine();
			int quantity = Integer.parseInt(s.nextLine());
			float paidPrice = Float.parseFloat(s.nextLine());
			
			currentSimulation.getInvestments().add(new Investment(symbol, companyName, quantity, paidPrice));			
			addStock(s); // Add the next investment from the file
		}
	}
	
	public static void saveSimulationToFile(File file) {
		PrintWriter printer;
		
		try {
			printer = new PrintWriter(file);
		} catch (FileNotFoundException e) {
			return;
		}
		
		printer.println(Float.toString(currentSimulation.getCashValue()));
	
		for(Investment investment: currentSimulation.getInvestments()) {
			printer.println(investment.getSymbol());
			printer.println(investment.getCompanyName());
			printer.println(investment.getQuantity());
			printer.println(investment.getPaidPrice());
		}
		
		printer.close();
	}

	public static Simulation getCurrentSimulation() {
		return currentSimulation;
	}
	
	public static DecimalFormat getCurrencyFormatter() {
		// Alter the built-in currency formatter so that it displays negative currencies
		DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance();
		
		formatter.setNegativePrefix("-" + formatter.getCurrency().getSymbol());
		formatter.setNegativeSuffix("");
		formatter.setMaximumFractionDigits(4);
		
		return formatter;
	}
}
