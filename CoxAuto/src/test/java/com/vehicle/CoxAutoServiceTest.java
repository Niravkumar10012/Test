package com.vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.coxAuto.dto.StockAvgPrice;
import com.coxAuto.dto.UserStockMapping;
import com.coxAuto.repository.CoxAutoRepository;
import com.coxAuto.service.CoxAutoService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CoxAutoServiceTest {
	
	@Mock
	private CoxAutoRepository coxAutoRepository;
	
	CoxAutoService service;
	
	@BeforeAll
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new CoxAutoService();
		ReflectionTestUtils.setField(service, "repository", coxAutoRepository);
	}

	@Test
	public void testSaveUserStockInvalidStock() {
		UserStockMapping userStock = new UserStockMapping();
		userStock.setStockCode("abcde");
		userStock.setUser("test user");
		UserStockMapping response = service.saveUserStock(userStock);
		assertNull(response);
	}
	
	@Test
	public void testSaveUserStockValidStockWithEmpty() {
		UserStockMapping userStock = new UserStockMapping();
		userStock.setStockCode("GOOG");
		userStock.setUser("test user");
		Optional<UserStockMapping> value = Optional.empty();
		Mockito.when(coxAutoRepository.findById("test user")).thenReturn(value);
		Mockito.when(coxAutoRepository.save(userStock)).thenReturn(userStock);
		
		UserStockMapping response = service.saveUserStock(userStock);
		assertEquals("GOOG",response.getStockCode());
		assertEquals("test user",response.getUser());
	}
	
	
	@Test
	public void testGetStockAvgPrices() throws IOException {
		Optional<UserStockMapping> value = Optional.of(new UserStockMapping("test user","GOOG"));
		Mockito.when(coxAutoRepository.findById("test user")).thenReturn(value);
		List<StockAvgPrice> avgPrices = service.getStockAvgPrices("test user");
		assertNotNull(avgPrices.get(0).getAvg());
	}	
	
	@Test
	public void testGetStockAvgPrice() throws IOException {
		Optional<UserStockMapping> value = Optional.of(new UserStockMapping("test user","GOOG"));
		Mockito.when(coxAutoRepository.findById("test user")).thenReturn(value);
		StockAvgPrice avgPrices = service.getStockAvgPrice("test user","GOOG");
		assertNotNull(avgPrices.getAvg());
	}	
}
