package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TransactionHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionHistory} and its DTO {@link TransactionHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {SubscriptionPlanMapper.class, UserProfileMapper.class})
public interface TransactionHistoryMapper extends EntityMapper<TransactionHistoryDTO, TransactionHistory> {

    @Mapping(source = "subscriptionPlan.id", target = "subscriptionPlanId")
    @Mapping(source = "userProfile.id", target = "userProfileId")
    TransactionHistoryDTO toDto(TransactionHistory transactionHistory);

    @Mapping(source = "subscriptionPlanId", target = "subscriptionPlan")
    @Mapping(source = "userProfileId", target = "userProfile")
    TransactionHistory toEntity(TransactionHistoryDTO transactionHistoryDTO);

    default TransactionHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setId(id);
        return transactionHistory;
    }
}
