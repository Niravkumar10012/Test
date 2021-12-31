package com.coxAuto.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class StockAvgPrice {
	private BigDecimal avg;
	private String stock;
}
