package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

    @InjectMocks
    private StockServiceImpl mockStockService;

    @Mock
    private StockRepository mockStockRepository;

    Stock stock = new Stock();
    List<Stock> stocks = new ArrayList<>();
    @Test
    void addStock() {
        stock.setIdStock(1L);
        stock.setTitle("stock one");
        when(mockStockRepository.save(stock)).thenReturn(stock);
        Stock newStock = mockStockService.addStock(stock);
        assertNotNull(newStock);
    }

    @Test
    void retrieveStock() {
        stock.setIdStock(1L);
        stock.setTitle("stock one");
        when(mockStockRepository.save(stock)).thenReturn(stock);
        when(mockStockRepository.findById(1L)).thenReturn(Optional.of(stock));
        Stock newStock = mockStockService.addStock(stock);
        Stock getStock = mockStockService.retrieveStock(newStock.getIdStock());
        assertEquals(newStock.getIdStock(),getStock.getIdStock());

    }

    @Test
    void retrieveAllStock() {
        stock.setIdStock(1L);
        stock.setTitle("stock one");
        stocks.add(stock);
        when(mockStockRepository.findAll()).thenReturn(stocks);
        List<Stock> stocksFromMockService = mockStockService.retrieveAllStock();
        assertNotNull(stocksFromMockService);
        assertEquals(stocksFromMockService.size(), 1);
    }
}