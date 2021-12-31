package com.coxAuto.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.coxAuto.dto.StockAvgPrice;
import com.coxAuto.dto.UserStockMapping;
import com.coxAuto.service.CoxAutoService;

@Validated
@RestController
public class CoxAutoController {

    @Autowired
    private CoxAutoService service;

    @PostMapping("/saveUserStockMapping")
    public UserStockMapping saveUserStockMapping(@RequestBody UserStockMapping userStock) {
        return service.saveUserStock(userStock);
    }

    @GetMapping("/getUserStocks/{userName}")
    public UserStockMapping getUserStocks(@PathVariable("userName") String userName) {
        return service.getUserStock(userName);
    }

    @GetMapping("/getUserStocksWithAvg/{userName}")
    public List<StockAvgPrice> getUserSocksWithAveragePrice(@PathVariable("userName") String userName) throws IOException {
    	return service.getStockAvgPrices(userName);
    }
    
    @GetMapping("/getUserStocksWithAvg/{userName}/{stockCode}")
    public StockAvgPrice getUserSocksWithAveragePrice(@PathVariable("userName") String userName, @PathVariable("stockCode") String stockCode) throws IOException {
    	return service.getStockAvgPrice(userName,stockCode);
    }
    


}