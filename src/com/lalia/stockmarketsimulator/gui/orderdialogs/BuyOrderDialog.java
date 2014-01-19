package com.lalia.stockmarketsimulator.gui.orderdialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.lalia.stockmarketsimulator.StockMarketSimulator;
import com.lalia.stockmarketsimulator.gui.ConfirmOrderDialog;

public class BuyOrderDialog extends OrderDialog implements ActionListener {
	public BuyOrderDialog(JFrame owner) {
		super(owner, "Buy Stock", "Buy");
	}
	
	@Override
	public void confirm(String symbol, int quantity, float price) {
		// Check if the user can afford to purchase the requested quantity of stock 
		if(StockMarketSimulator.getCurrentSimulation().getCashValue() > price * quantity) {
			ConfirmOrderDialog confirmDialog = new ConfirmOrderDialog(this,	"Confirm Buy Order", "Buy", symbol, quantity);
			
			confirmDialog.addListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {
					String symbol = symbolTextField.getText();
					int quantity = (int) quantitySpinner.getValue();
					
					System.out.println(symbol);
					System.out.println(quantity);
					
					StockMarketSimulator.getCurrentSimulation().buyStock(symbol, quantity);	
				}
			});
			
			confirmDialog.setVisible(true);
		}
		else {				
			JOptionPane.showMessageDialog(this, 
					"You lack the required funds to purchase the requested quantity of stock.",
				    "Insufficient Funds",
				    JOptionPane.ERROR_MESSAGE);
		}
	}
}
