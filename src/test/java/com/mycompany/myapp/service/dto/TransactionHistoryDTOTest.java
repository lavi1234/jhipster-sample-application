package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TransactionHistoryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionHistoryDTO.class);
        TransactionHistoryDTO transactionHistoryDTO1 = new TransactionHistoryDTO();
        transactionHistoryDTO1.setId(1L);
        TransactionHistoryDTO transactionHistoryDTO2 = new TransactionHistoryDTO();
        assertThat(transactionHistoryDTO1).isNotEqualTo(transactionHistoryDTO2);
        transactionHistoryDTO2.setId(transactionHistoryDTO1.getId());
        assertThat(transactionHistoryDTO1).isEqualTo(transactionHistoryDTO2);
        transactionHistoryDTO2.setId(2L);
        assertThat(transactionHistoryDTO1).isNotEqualTo(transactionHistoryDTO2);
        transactionHistoryDTO1.setId(null);
        assertThat(transactionHistoryDTO1).isNotEqualTo(transactionHistoryDTO2);
    }
}
