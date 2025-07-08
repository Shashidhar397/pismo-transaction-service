package com.pismo.assessment.repository;

import com.pismo.assessment.entity.OperationType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OperationTypeRepositoryTest {

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @BeforeEach
    void setUp() {
        OperationType operationType = new OperationType();
        operationType.setOperationTypeId(1L);
        operationType.setDescription("Withdrawal");
        operationTypeRepository.save(operationType);
    }

    @AfterEach
    void tearDown() {
        operationTypeRepository.deleteAll();
    }

    @Test
    void findByOperationTypeId_Found() {
        Long operationTypeId = 1L;
        Optional<OperationType> found = operationTypeRepository.findByOperationTypeId(operationTypeId);

        assertTrue(found.isPresent());
        assertEquals(operationTypeId, found.get().getOperationTypeId());
        assertEquals("Withdrawal", found.get().getDescription());
    }

    @Test
    void findByOperationTypeId_NotFound() {
        Long operationTypeId = 999L; // Assuming this ID does not exist
        Optional<OperationType> found = operationTypeRepository.findByOperationTypeId(operationTypeId);

        assertFalse(found.isPresent());
    }
}