package com.coxAuto.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coxAuto.dto.StockAvgPrice;
import com.coxAuto.dto.UserStockMapping;
import com.coxAuto.repository.CoxAutoRepository;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;

@Service
public class CoxAutoService {

	@Autowired
    private CoxAutoRepository repository;
	
	public UserStockMapping saveUserStock(UserStockMapping userStock) {
		
		if(!validateStockList(userStock.getStockCode())) {
			return null;
		}
		
		Optional<UserStockMapping> existingStock = repository.findById(userStock.getUser());
		List<String> stockCodeList = new ArrayList<>();
		if(existingStock.isPresent()) {
			stockCodeList = Arrays.asList(existingStock.get().getStockCode().split(","));
		} 
		Set<String> newStockCode = new HashSet<>(stockCodeList);
		newStockCode.addAll(Arrays.asList(userStock.getStockCode().split(",")));
		String stockCode = String.join(",", newStockCode);
		userStock.setStockCode(stockCode);
		return repository.save(userStock);
	}

	private boolean validateStockList(String stockCode) {
		boolean isValid = false;
		for(String stock : Arrays.asList(stockCode.split(","))) {
			try {
				if(YahooFinance.get("INTC").isValid()){
					isValid = true;
				}else {
					return isValid;
				}
			} catch (IOException e) {
				return isValid;
			}
			
		}
		return isValid;
	}

	public UserStockMapping getUserStock(String userName) {
		Optional<UserStockMapping> existingStock = repository.findById(userName);
		if(existingStock.isPresent()) {
			return existingStock.get();
		}
		return null;
	}
	
	public List<StockAvgPrice> getStockAvgPrices(String userName) throws IOException{
		Optional<UserStockMapping> existingStock = repository.findById(userName);
		List<StockAvgPrice> avgPrices = new ArrayList<>();
		if(!existingStock.isPresent()) {
			return null;
		} else {
			avgPrices= getAvgPrices(existingStock.get());
		}
		return avgPrices;
	}

	private List<StockAvgPrice> getAvgPrices(UserStockMapping userStockMapping) throws IOException {
		List<StockAvgPrice> avgPriceList = new ArrayList<>();
		for(String stockCode : userStockMapping.getStockCode().split(",")) {
			StockAvgPrice avgPrice = getAvgPrice(stockCode);
			avgPriceList.add(avgPrice);
		}
		return avgPriceList;
	}

	public StockAvgPrice getAvgPrice(String stockCode) throws IOException {
		Stock stock = YahooFinance.get(stockCode, true);
		List<HistoricalQuote> history = stock.getHistory();
		BigDecimal avg = BigDecimal.ZERO;
		BigDecimal total = BigDecimal.ZERO;
		for(HistoricalQuote his : history) {
			total = total.add(his.getHigh().subtract(his.getLow()));
		}
		avg = total.divide(new BigDecimal(history.size()), 4, RoundingMode.HALF_UP);
		StockAvgPrice avgPrice = new StockAvgPrice();
		avgPrice.setAvg(avg);
		avgPrice.setStock(stockCode);
		return avgPrice;
	}

	public StockAvgPrice getStockAvgPrice(String userName, String stockCode) throws IOException {
		StockAvgPrice avgPrice = new StockAvgPrice();
		avgPrice.setAvg(getAvgPrice(stockCode).getAvg());
		avgPrice.setStock(stockCode);
		return avgPrice;
	}
}
