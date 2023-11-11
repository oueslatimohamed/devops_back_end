package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.OperatorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OperatorServiceImplTest {
    @InjectMocks
    private OperatorServiceImpl mockOperatorService;

    @Mock
    private OperatorRepository mockOperatorRepository;
    List<Operator> operators = new ArrayList<>();
    Operator operator = new Operator();
    @Test
    void retrieveAllOperators() {
        operator.setIdOperateur(1L);
        operators.add(operator);
        when(mockOperatorRepository.findAll()).thenReturn(operators);
        List<Operator> operators = mockOperatorService.retrieveAllOperators();
        assertNotNull(operators);
        assertEquals(operators.size(),1);
    }

    @Test
    void addOperator() {
        operator.setIdOperateur(1L);
        when(mockOperatorRepository.save(operator)).thenReturn(operator);
        Operator newOperator = mockOperatorService.addOperator(operator);
        assertNotNull(newOperator);
        assertEquals(newOperator.getIdOperateur(),1L);
    }

    @Test
    void deleteOperator() {
        mockOperatorService.deleteOperator(Mockito.anyLong());
        verify(mockOperatorRepository).deleteById(Mockito.anyLong());
    }

    @Test
    void updateOperator() {
        operator.setIdOperateur(1L);
        when(mockOperatorRepository.save(operator)).thenReturn(operator);
        Operator updatedOperator = mockOperatorService.updateOperator(operator);
        assertNotNull(updatedOperator);
        assertEquals(updatedOperator.getIdOperateur(),1L);
    }

    @Test
    void retrieveOperator() {
        operator.setIdOperateur(1L);
        when(mockOperatorRepository.findById(1L)).thenReturn(Optional.of(operator));
        Operator getOperator = mockOperatorService.retrieveOperator(1L);
        assertNotNull(getOperator);
        assertEquals(1L, getOperator.getIdOperateur());
    }
}