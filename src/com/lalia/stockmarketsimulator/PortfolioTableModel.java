package com.lalia.stockmarketsimulator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.table.AbstractTableModel;


public class PortfolioTableModel extends AbstractTableModel {
	private Map<Investment, Boolean> selectedRows;
	
	public PortfolioTableModel() {
		StockMarketSimulator.getCurrentSimulation().getInvestments();
		selectedRows = new HashMap<Investment, Boolean>();
	}
	
	public List<String> getSelectedStocks() {
		List<String> selectedStocks = new ArrayList<String>();
		
		Iterator<Entry<Investment, Boolean>> it = selectedRows.entrySet().iterator();
	    while (it.hasNext()) {
	        Entry<Investment, Boolean> pairs = it.next();
	        if((boolean)pairs.getValue() == true) {
	        	selectedStocks.add(pairs.getKey().getSymbol());
	        }
	    }
	    
	    return selectedStocks;
	}

	@Override
	public int getColumnCount() {
		return 10;
	}
	
	@Override
	public String getColumnName(int col) {
		String[] columnNames = {" ", "Company", "Symbol", "Paid/Share", "Price/Share", "Change", "Quantity", "Initial Value", "Current Value", "Profit/Loss"};//, "%"};
		return columnNames[col];
	}

	@Override
	public int getRowCount() {
		return StockMarketSimulator.getCurrentSimulation().getInvestments().size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		List<Investment> currentInvestments = StockMarketSimulator.getCurrentSimulation().getInvestments();
		
		// This allows us to only display two decimals
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		
		switch(col) {
		case 0:
			Investment key = currentInvestments.get(row);
			return selectedRows.containsKey(key) ? selectedRows.get(key) : false;
		case 1:
			return currentInvestments.get(row).getCompanyName();
		case 2:
			return currentInvestments.get(row).getSymbol();
		case 3:
			return new Float(currentInvestments.get(row).getPaidPrice());
		case 4:
			return new Float(currentInvestments.get(row).getCurrentPrice());
		case 5:
			return new Float(currentInvestments.get(row).getChangeInPrice());
		case 6:
			return new Float(currentInvestments.get(row).getQuantity());
		case 7:
			return new Float(currentInvestments.get(row).getInitialValue());
		case 8:
			return new Float(currentInvestments.get(row).getValue());
		case 9:
			return new Float(currentInvestments.get(row).getProfitLoss());	
		default:
			return null;
		}
	}
	
	@Override
	public void setValueAt(Object value, int row, int col) {
		if(col == 0) {
			selectedRows.put(StockMarketSimulator.getCurrentSimulation().getInvestments().get(row), (Boolean) value);
		}
	}
	
	 public boolean isCellEditable(int row, int col) {
        if(col == 0) return true;
        return false;
	 }
	
	@Override
	public Class<? extends Object> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

}
