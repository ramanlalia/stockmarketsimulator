package com.lalia.stockmarketsimulator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.lalia.stockmarketsimulator.StockMarketSimulator;
import com.lalia.stockmarketsimulator.gui.orderdialogs.BuyOrderDialog;
import com.lalia.stockmarketsimulator.gui.orderdialogs.SellOrderDialog;

public class MenuBar extends JMenuBar implements ActionListener {
	JMenuItem buyMenuItem, sellMenuItem, openMenuItem, saveMenuItem, exitMenuItem;
	
	public MenuBar() {
		createFileMenu();
		
		exitMenuItem.addActionListener(this);
		buyMenuItem.addActionListener(this);
		sellMenuItem.addActionListener(this);
		saveMenuItem.addActionListener(this);
		
		
		/*sellStockMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem sourceButton = (JMenuItem) e.getSource();
				JFrame parentFrame = (JFrame) SwingUtilities.getRoot(sourceButton);
				
				if(portfolioTableModel.getSelectedStocks().isEmpty()) {
					JOptionPane.showMessageDialog(parentFrame, "Please select the stocks you wish to sell.", "No Stocks Selected", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				for(String symbol: portfolioTableModel.getSelectedStocks()) {
					String quantity = (String) JOptionPane.showInputDialog(
							parentFrame,
		                    "How many shares of " + symbol + " would you like to sell?",
		                    "Amount of Shares",
		                    JOptionPane.PLAIN_MESSAGE);
					
					if(quantity != null && quantity.length() > 0)
						StockMarketSimulator.getCurrentSimulation().sellStock(symbol, Integer.parseInt(quantity));
				}
			}
		});*/
		
		openMenuItem.addActionListener(this);
	}
	
	private void createFileMenu() {
		JMenu fileMenu = new JMenu("File");
		
		openMenuItem = new JMenuItem("Open...");
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		
		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		
		exitMenuItem = new JMenuItem("Exit");
		
		JMenu newMenu = new JMenu("New");
		
		buyMenuItem = new JMenuItem("Buy...");
		buyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
		
		sellMenuItem = new JMenuItem("Sell...");
		sellMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		
		newMenu.add(buyMenuItem);
		newMenu.add(sellMenuItem);
		
		fileMenu.add(newMenu);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(new JSeparator());
		fileMenu.add(exitMenuItem);
		
		add(fileMenu);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(exitMenuItem)) {
			getParentFrame().dispose();
		}
		else if(e.getSource().equals(buyMenuItem) || e.getActionCommand() == "BUY_STOCK") {
			BuyOrderDialog orderDialog = new BuyOrderDialog(getParentFrame());
			orderDialog.setVisible(true);	
		}
		else if(e.getSource().equals(sellMenuItem)) {
			SellOrderDialog orderDialog = new SellOrderDialog(getParentFrame());
			orderDialog.setVisible(true);	
		}
		else if(e.getSource().equals(openMenuItem)) {
			JFileChooser fileChooser = new JFileChooser();
	        if (fileChooser.showOpenDialog(getParentFrame()) == JFileChooser.APPROVE_OPTION)
	            StockMarketSimulator.readSimulationFromFile(fileChooser.getSelectedFile());
		}
		else if(e.getSource().equals(saveMenuItem)) {			
			// This file chooser is customized such that it asks the user
			// before it overwrites a file
			JFileChooser fc = new JFileChooser(){
				 @Override
			    public void approveSelection(){
			        File f = getSelectedFile();
			        if(f.exists() && getDialogType() == SAVE_DIALOG){
			            
			        	// Ask the user
			        	int result = JOptionPane.showConfirmDialog(this,
			            		"The file already exists. Would you like to overwrite it?",
			            		"Overwrite", JOptionPane.YES_NO_OPTION);
			            
			        	// Approve the selection if the user clicked "Yes"
			            switch(result){
			                case JOptionPane.YES_OPTION:
			                    super.approveSelection();
			                    return;
			            }
			        }
			        
			        super.approveSelection();
			    }
			};
			
			if (fc.showSaveDialog(getParentFrame()) == JFileChooser.APPROVE_OPTION)
	            StockMarketSimulator.saveSimulationToFile(fc.getSelectedFile());
		}
	}
	
	// Returns this menu bar's frame
	private JFrame getParentFrame() {
		return (JFrame) SwingUtilities.getWindowAncestor(this);
	}
	
}
