package com.lalia.stockmarketsimulator.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.lalia.stockmarketsimulator.StockMarket;
import com.lalia.stockmarketsimulator.StockMarketSimulator;

public class ConfirmOrderDialog extends JDialog implements ActionListener {
	private List<ActionListener> actionListeners;
	
	// GUI components
	JTextField companyNameTextField, symbolTextField, quantityTextField, priceTextField, totalPriceTextField;
	JButton confirmButton, cancelButton;		
			
	public ConfirmOrderDialog(Dialog owner, String title, String confirmButtonText, String symbol, int quantity) {
		super(owner, title, true);
		setLocationByPlatform(true);
		
		confirmButton = new JButton(confirmButtonText);
		getRootPane().setDefaultButton(confirmButton);
		
		this.actionListeners = new ArrayList<ActionListener>();
				
		String companyName = StockMarket.getCompanyName(symbol);		
		float price = StockMarket.getPrice(symbol);
		String priceString = StockMarketSimulator.getCurrencyFormatter().format(price); // Format the price as a currency
		String totalPriceString = StockMarketSimulator.getCurrencyFormatter().format(price * quantity);

		initializeGUIComponents();
		constructGUI();
		
		companyNameTextField.setText(companyName);		
		symbolTextField.setText(symbol);
		quantityTextField.setText(Integer.toString(quantity));
		priceTextField.setText(priceString);
		totalPriceTextField.setText(totalPriceString);
		
		confirmButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		cancelButton.requestFocus();
	}
	
	private void initializeGUIComponents() {
		companyNameTextField = new JTextField(15);		
		symbolTextField = new JTextField(15);		
		quantityTextField = new JTextField(15);		
		priceTextField = new JTextField(15);		
		totalPriceTextField = new JTextField(15);
		
		for(JTextField textField: new JTextField[] {companyNameTextField, 
				symbolTextField, quantityTextField, priceTextField,
				totalPriceTextField}) textField.setEditable(false);
		
		cancelButton = new JButton("Cancel");		
	}
	
	private void constructGUI() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		gridBagConstraints.gridy = 0;
		panel.add(new JLabel("Company"), gridBagConstraints);
		
		gridBagConstraints.gridy++;
		panel.add(companyNameTextField, gridBagConstraints);
		
		gridBagConstraints.gridy++;
		gridBagConstraints.insets = new Insets(15, 0, 0, 0);
		panel.add(new JLabel("Stock Symbol"), gridBagConstraints);
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		
		gridBagConstraints.gridy++;
		panel.add(symbolTextField, gridBagConstraints);
		
		gridBagConstraints.gridy++;
		gridBagConstraints.insets = new Insets(15, 0, 0, 0);
		panel.add(new JLabel("Quantity"), gridBagConstraints);
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		
		gridBagConstraints.gridy++;
		panel.add(quantityTextField, gridBagConstraints);
		
		gridBagConstraints.gridy++;
		gridBagConstraints.insets = new Insets(15, 0, 0, 0);
		panel.add(new JLabel("Price/Share"), gridBagConstraints);
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		
		gridBagConstraints.gridy++;
		panel.add(priceTextField, gridBagConstraints);
		
		gridBagConstraints.gridy++;
		gridBagConstraints.insets = new Insets(15, 0, 0, 0);
		panel.add(new JLabel("Total Price"), gridBagConstraints);
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		
		gridBagConstraints.gridy++;
		panel.add(totalPriceTextField, gridBagConstraints);
		
		gridBagConstraints.gridy++;
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		panel.add(new JSeparator(), gridBagConstraints);
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		buttonsPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(confirmButton);
		
		gridBagConstraints.gridy++;
		panel.add(buttonsPanel, gridBagConstraints);
		
		getContentPane().add(panel, BorderLayout.CENTER);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(cancelButton)) {
			dispose();
		}
		else if(e.getSource().equals(confirmButton)) {
			invokeListeners();
			getOwner().dispose();
		}
	}
	
	public void addListener(ActionListener actionListener) {
		actionListeners.add(actionListener);
	}
	
	public void invokeListeners() {
		for(ActionListener actionListener: actionListeners) {
			actionListener.actionPerformed(null);
		}
	}
}
