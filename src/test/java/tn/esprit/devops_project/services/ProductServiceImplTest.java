package tn.esprit.devops_project.services;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl mockProductService;
    @Mock
    private ProductRepository mockProductRepository;
    @Mock
    private StockRepository mockStockRepository;

    // init object for test
    Stock stock = new Stock();
    Product product = new Product();
    Product product1 = new Product();

    List<Product> products = new ArrayList<>();

    @Test
    void addProduct() {
        product.setTitle("Test Product");
        product.setPrice(19.99f);
        product.setQuantity(50);
        product.setCategory(ProductCategory.ELECTRONICS);
        when(mockStockRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(stock));
        when(mockProductRepository.save(product)).thenReturn(product);
        Product newProduct = mockProductService.addProduct(product, 1L);

        assertNotNull(newProduct);
        assertEquals(stock, newProduct.getStock());
        verify(mockStockRepository).findById(Mockito.anyLong());
        verify(mockProductRepository).save(product);
    }

    @Test
    void retrieveProduct() {
        when(mockProductRepository.findById(1L)).thenReturn(Optional.of(product));

        Product retrievedProduct = mockProductService.retrieveProduct(1L);

        assertNotNull(retrievedProduct);
        assertEquals(product, retrievedProduct);

        verify(mockProductRepository).findById(Mockito.anyLong());
    }

    @Test
    void retreiveAllProduct() {
        products.add(product);
        products.add(product1);

        when(mockProductRepository.findAll()).thenReturn(products);

        List<Product> productsFromMockService = mockProductService.retreiveAllProduct();

        assertNotNull(productsFromMockService);
        assertEquals(productsFromMockService.size(), 2);

        verify(mockProductRepository).findAll();
    }

    @Test
    void retrieveProductByCategory() {
        product.setCategory(ProductCategory.ELECTRONICS);
        product1.setCategory(ProductCategory.BOOKS);
        products.add(product);
        products.add(product1);
        when(mockProductRepository.findByCategory(ProductCategory.BOOKS)).thenReturn(List.of(product1));
        List<Product> productsFromMockService = mockProductService.retrieveProductByCategory(ProductCategory.BOOKS);
        assertNotNull(productsFromMockService);
        assertEquals(productsFromMockService.size(), 1);
        assertEquals(productsFromMockService.get(0).getCategory(), product1.getCategory());

    }

    @Test
    void deleteProduct() {
        mockProductService.deleteProduct(Mockito.anyLong());
        verify(mockProductRepository).deleteById(Mockito.anyLong());
    }

    @Test
    void retreiveProductStock() {
        product.setStock(stock);
        when(mockProductRepository.findByStockIdStock(Mockito.anyLong())).thenReturn(List.of(product));

        List<Product> productsFromMockService = mockProductService.retreiveProductStock(Mockito.anyLong());

        assertNotNull(productsFromMockService);
        assertEquals(productsFromMockService.size(), 1);
        assertEquals(productsFromMockService.get(0).getStock().getIdStock(), stock.getIdStock());

        verify(mockProductRepository).findByStockIdStock(Mockito.anyLong());
    }
}