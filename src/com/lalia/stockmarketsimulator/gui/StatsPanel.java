package com.lalia.stockmarketsimulator.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.lalia.stockmarketsimulator.StockMarketSimulator;

public class StatsPanel extends JPanel {
	private JTextField cashValueTextField, portfolioValueTextField, totalValueTextField;
	
	public StatsPanel() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		cashValueTextField = createStatDisplay("Cash", StockMarketSimulator.getCurrentSimulation().getCashValue());
		add(Box.createHorizontalStrut(30));  // Add space between the components	
		portfolioValueTextField = createStatDisplay("Portfolio Value", StockMarketSimulator.getCurrentSimulation().getPortfolioValue());
		add(Box.createHorizontalStrut(30));		
		totalValueTextField = createStatDisplay("Total Value", StockMarketSimulator.getCurrentSimulation().getTotalValue());
	
		// This listener updates the stat display when the user buys or sell a stock
		StockMarketSimulator.getCurrentSimulation().addListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				float cashValue = StockMarketSimulator.getCurrentSimulation().getCashValue();
				float portfolioValue = StockMarketSimulator.getCurrentSimulation().getPortfolioValue();
				float totalValue = StockMarketSimulator.getCurrentSimulation().getTotalValue();
				
				// Format the values as currencies
				String cashValueStr = StockMarketSimulator.getCurrencyFormatter().format(cashValue);
				String portfolioValueStr = StockMarketSimulator.getCurrencyFormatter().format(portfolioValue);
				String totalValueStr = StockMarketSimulator.getCurrencyFormatter().format(totalValue);
				
				cashValueTextField.setText(cashValueStr);
				portfolioValueTextField.setText(portfolioValueStr);
				totalValueTextField.setText(totalValueStr);
			}
		});
	}
	
	private JTextField createStatDisplay(String name, float value) {
		GridLayout gridLayout = new GridLayout(2, 1);
		
		JPanel panel = new JPanel(gridLayout);
		JLabel label = new JLabel(name);
		
		JTextField valueTextField = new JTextField("$" + Float.toString(value), 15);
		valueTextField.setEditable(false);
		
		panel.add(label);
		panel.add(valueTextField);		
		add(panel);
		
		return valueTextField;
	}
}
