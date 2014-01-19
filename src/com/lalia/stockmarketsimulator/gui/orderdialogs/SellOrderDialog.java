package com.lalia.stockmarketsimulator.gui.orderdialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.lalia.stockmarketsimulator.Investment;
import com.lalia.stockmarketsimulator.StockMarketSimulator;
import com.lalia.stockmarketsimulator.gui.ConfirmOrderDialog;

public class SellOrderDialog extends OrderDialog {
	public SellOrderDialog(JFrame owner) {
		super(owner, "Sell Stock", "Sell");
	}
	
	@Override
	public void confirm(String symbol, int quantity, float price) {
		boolean investmentExists = false;
		int maxQuantity = 0;
		
		for(Investment investment: StockMarketSimulator.getCurrentSimulation().getInvestments()) {
			if(investment.getSymbol().equals(symbol)) {
				investmentExists = true;
				maxQuantity = investment.getQuantity();
			}
		}
		
		if(investmentExists == false) {
			JOptionPane.showMessageDialog(this, 
					"You don't have any amount of that stock.",
				    "Nonexistent Stock",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(quantity > maxQuantity) {
			JOptionPane.showMessageDialog(this, 
					"You don't have that many shares to sell.",
				    "Insufficient Shares",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		ConfirmOrderDialog confirmDialog = new ConfirmOrderDialog(this,	"Confirm Sell Order", "Sell", symbol, quantity);
		
		confirmDialog.addListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String symbol = symbolTextField.getText();
				int quantity = (int) quantitySpinner.getValue();
				
				StockMarketSimulator.getCurrentSimulation().sellStock(symbol, quantity);
			}
		});
		
		confirmDialog.setVisible(true);
	}
}
