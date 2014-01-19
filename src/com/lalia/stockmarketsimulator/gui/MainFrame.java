package com.lalia.stockmarketsimulator.gui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.lalia.stockmarketsimulator.PortfolioTableModel;
import com.lalia.stockmarketsimulator.StockMarketSimulator;

public class MainFrame extends JFrame implements ActionListener {
	private PortfolioTableModel portfolioTableModel;
	
	public MainFrame() {
		MenuBar menuBar = new MenuBar();
		
		setTitle("Stock Market Simulator");
		setLocationByPlatform(true);
		setJMenuBar(menuBar);
		
		// This panel displays the cash value, portfolio value, and total value
		StatsPanel statsPanel = new StatsPanel();		
		statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the panel
		getContentPane().add(statsPanel, BorderLayout.NORTH);
		
		createPortfolioTable();
		
		JButton sellStockButton = new JButton("Sell Selected Stocks");
		
		JButton buyButton = new JButton("Buy Stock");
		buyButton.setActionCommand("BUY_STOCK");
		buyButton.addActionListener(menuBar);
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		buttonsPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
		buttonsPanel.add(sellStockButton);
		buttonsPanel.add(buyButton);
		
		getContentPane().add(buttonsPanel, BorderLayout.PAGE_END);
		
		pack();
		
		// This event fires when investments are changed (e.g. added, sold)
		StockMarketSimulator.getCurrentSimulation().addListener(this);		
	}
	
	private void createPortfolioTable() {
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
		
		// This model dynamically supplies the table with data
		portfolioTableModel = new PortfolioTableModel();
		
		JTable table = new JTable(portfolioTableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(0).setResizable(false);
		
		// Allow the table to be sorted
		table.setAutoCreateRowSorter(true);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(800, 200));	
		
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(tablePanel, BorderLayout.CENTER);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Tell the table that the portfolio changed
		portfolioTableModel.fireTableDataChanged();
	}
}
