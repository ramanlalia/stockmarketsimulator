package com.lalia.stockmarketsimulator.gui.orderdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import com.lalia.stockmarketsimulator.StockMarket;

public class OrderDialog extends JDialog implements ActionListener {	
	protected JTextField symbolTextField = new JTextField(15);	
	protected JButton confirmButton, cancelButton = new JButton("Cancel");	
	protected JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
	
	public OrderDialog(JFrame owner, String title, String confirmButtonText) {
		super(owner, title, true);
		
		setLocationByPlatform(true);
		setResizable(false);
		
		confirmButton = new JButton(confirmButtonText);
		getRootPane().setDefaultButton(confirmButton);
		
		constructGUI();
		
		cancelButton.addActionListener(this);
		confirmButton.addActionListener(this);		
	}
	
	private void constructGUI() {
		JPanel mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;		
		mainPanel.add(new JLabel("Stock Symbol"), gridBagConstraints);
				
		gridBagConstraints.gridx = 1;
		mainPanel.add(new JLabel("Quantity"), gridBagConstraints);
		
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 0, 0, 5);
		mainPanel.add(symbolTextField, gridBagConstraints);
		
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		Dimension dimensions = new Dimension(quantitySpinner.getPreferredSize());
		dimensions.width = 75;
		quantitySpinner.setPreferredSize(dimensions);		
		mainPanel.add(quantitySpinner, gridBagConstraints);
				
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		mainPanel.add(new JSeparator(), gridBagConstraints);
		
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		buttonsPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(confirmButton);
		
		mainPanel.add(buttonsPanel, gridBagConstraints);
		
		mainPanel.setBorder(new EmptyBorder(10, 10, 5, 10));
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(confirmButton)) {
			if(symbolTextField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this,
						"Please enter a stock symbol.",
						"Enter Stock Symbol",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String stockSymbol = symbolTextField.getText();
			int quantity = (int) quantitySpinner.getValue();
			
			if(StockMarket.exists(stockSymbol) == false) {
				JOptionPane.showMessageDialog(this,
						"The stock symbol you requested does not exist.",
						"Invalid Stock Symbol",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			float stockPrice = StockMarket.getPrice(stockSymbol);
			
			confirm(stockSymbol, quantity, stockPrice);		
		}
		else if(e.getSource().equals(cancelButton)) {
			dispose();
		}
	}
	
	// This method's functionality is provided by the sub-classes BuyOrderDialog and SellOrderDialog
	public void confirm(String symbol, int quantity, float price) {}
}
