package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionHistoryMapperTest {

    private TransactionHistoryMapper transactionHistoryMapper;

    @BeforeEach
    public void setUp() {
        transactionHistoryMapper = new TransactionHistoryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(transactionHistoryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(transactionHistoryMapper.fromId(null)).isNull();
    }
}
