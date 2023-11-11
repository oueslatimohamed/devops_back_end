package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.entities.SupplierCategory;
import tn.esprit.devops_project.repositories.SupplierRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplierServiceImplTest {
    @InjectMocks
    private SupplierServiceImpl mockSupplierService;

    @Mock
    private SupplierRepository mockSupplierRepository;
    Supplier supplier = new Supplier(1L,"S001", "Supplier1", SupplierCategory.ORDINAIRE,new HashSet<>());
    List<Supplier> suppliers = new ArrayList<>();

    @Test
    void retrieveAllSuppliers() {
        suppliers.add(supplier);
        when(mockSupplierRepository.findAll()).thenReturn(suppliers);
        List<Supplier> suppliersFromMockService = mockSupplierService.retrieveAllSuppliers();
        assertNotNull(suppliersFromMockService);
        assertEquals(suppliersFromMockService.size(), 1);
    }

    @Test
    void addSupplier() {
        supplier.setIdSupplier(1L);
        when(mockSupplierRepository.save(supplier)).thenReturn(supplier);
        Supplier newSupplier = mockSupplierService.addSupplier(supplier);
        assertNotNull(newSupplier);
        assertEquals(newSupplier.getIdSupplier(),supplier.getIdSupplier());
    }

    @Test
    void updateSupplier() {
        supplier.setCode("T039");
        when(mockSupplierRepository.save(supplier)).thenReturn(supplier);
        Supplier updatedOperator = mockSupplierService.updateSupplier(supplier);
        assertNotNull(updatedOperator);
        assertEquals(updatedOperator.getCode(),"T039");
    }

    @Test
    void deleteSupplier() {
        mockSupplierService.deleteSupplier(supplier.getIdSupplier());
        verify(mockSupplierRepository).deleteById(supplier.getIdSupplier());
    }

    @Test
    void retrieveSupplier() {
        when(mockSupplierRepository.save(supplier)).thenReturn(supplier);
        Supplier newSupplier = mockSupplierService.addSupplier(supplier);
        when(mockSupplierRepository.findById(newSupplier.getIdSupplier())).thenReturn(Optional.of(newSupplier));
        Supplier getSupplier = mockSupplierService.retrieveSupplier(newSupplier.getIdSupplier());
        assertNotNull(getSupplier);
        assertEquals(newSupplier.getIdSupplier(),getSupplier.getIdSupplier());
    }
}