package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.UserSubscriptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserSubscription} and its DTO {@link UserSubscriptionDTO}.
 */
@Mapper(componentModel = "spring", uses = {SubscriptionPlanMapper.class, UserProfileMapper.class})
public interface UserSubscriptionMapper extends EntityMapper<UserSubscriptionDTO, UserSubscription> {

    @Mapping(source = "subscriptionPlan.id", target = "subscriptionPlanId")
    @Mapping(source = "userProfile.id", target = "userProfileId")
    UserSubscriptionDTO toDto(UserSubscription userSubscription);

    @Mapping(source = "subscriptionPlanId", target = "subscriptionPlan")
    @Mapping(source = "userProfileId", target = "userProfile")
    UserSubscription toEntity(UserSubscriptionDTO userSubscriptionDTO);

    default UserSubscription fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setId(id);
        return userSubscription;
    }
}
