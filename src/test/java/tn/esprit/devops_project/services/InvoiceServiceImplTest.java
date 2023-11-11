package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceImplTest {
    @InjectMocks
    private InvoiceServiceImpl mockInvoiceService;

    @Mock
    private InvoiceRepository mockInvoiceRepository;

    @Mock
    private OperatorRepository mockOperatorRepository;

    @Mock
    private SupplierRepository mockSupplierRepository;

    List<Invoice> invoices = List.of(
            new Invoice(1L, 50.0f, 100.0f, new Date(), new Date(), false, null, null),
            new Invoice(2L, 100.0f, 200.0f, new Date(), new Date(), false, null, null)
    );
    Set<Invoice> invoicesSet = Set.of(
            new Invoice(1L, 50.0f, 100.0f, new Date(), new Date(), false, null, null),
            new Invoice(2L, 100.0f, 200.0f, new Date(), new Date(), false, null, null)
    );
    Invoice invoice = new Invoice(1L, 100.0f, 200.0f, new Date(), new Date(), false, null, null);
    Supplier supplier = new Supplier();
    Operator operator = new Operator();

    @Test
    void retrieveAllInvoices() {
        when(mockInvoiceRepository.findAll()).thenReturn(invoices);
        List<Invoice> invoicesFromMockService = mockInvoiceService.retrieveAllInvoices();
        assertNotNull(invoicesFromMockService);
        assertEquals(invoicesFromMockService.size(), 2);
    }

    @Test
    void cancelInvoice() {
        when(mockInvoiceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(invoice));
        when(mockInvoiceRepository.save(invoice)).thenReturn(invoice);
        mockInvoiceService.cancelInvoice(Mockito.anyLong());
        assertTrue(invoice.getArchived());
    }

    @Test
    void retrieveInvoice() {
        when(mockInvoiceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(invoice));
        Invoice getInvoice = mockInvoiceService.retrieveInvoice(Mockito.anyLong());
        assertNotNull(getInvoice);
        assertEquals(getInvoice.getIdInvoice(), 1L);
    }

    @Test
    void getInvoicesBySupplier() {
        supplier.setIdSupplier(1L);
        supplier.setInvoices(invoicesSet);
        when(mockSupplierRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(supplier));
        List<Invoice> invoicesFromMockService = mockInvoiceService.getInvoicesBySupplier(1L);
        assertNotNull(invoicesFromMockService);
        assertEquals(invoicesFromMockService.size(), 2);
    }

    @Test
    void assignOperatorToInvoice() {
        operator.setIdOperateur(2L);
        operator.setInvoices(new HashSet<>());
        when(mockInvoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));
        when(mockOperatorRepository.findById(2L)).thenReturn(Optional.of(operator));

        mockInvoiceService.assignOperatorToInvoice(operator.getIdOperateur(), invoice.getIdInvoice());
        assertNotNull(operator.getInvoices());
        verify(mockOperatorRepository).save(operator);

        assertTrue(operator.getInvoices().contains(invoice));
    }

    @Test
    void getTotalAmountInvoiceBetweenDates() {
        when(mockInvoiceRepository.getTotalAmountInvoiceBetweenDates(Mockito.any(Date.class), Mockito.any(Date.class))).thenReturn(1000f);
        float totalAmount = mockInvoiceService.getTotalAmountInvoiceBetweenDates(new Date(), new Date());
        assertEquals(totalAmount, 1000);
    }
}