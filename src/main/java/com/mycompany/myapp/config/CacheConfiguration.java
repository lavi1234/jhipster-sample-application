package com.mycompany.myapp.config;

import io.github.jhipster.config.JHipsterProperties;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mycompany.myapp.domain.User.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName());
            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mycompany.myapp.domain.Address.class.getName());
            createCache(cm, com.mycompany.myapp.domain.AppFeatures.class.getName());
            createCache(cm, com.mycompany.myapp.domain.AppFeatures.class.getName() + ".subsriptionPlanFeatures");
            createCache(cm, com.mycompany.myapp.domain.Bonus.class.getName());
            createCache(cm, com.mycompany.myapp.domain.BonusReference.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Cart.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Category.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Category.class.getName() + ".userCategoryMappings");
            createCache(cm, com.mycompany.myapp.domain.Commission.class.getName());
            createCache(cm, com.mycompany.myapp.domain.CommissionReference.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Company.class.getName());
            createCache(cm, com.mycompany.myapp.domain.EmailTracking.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Enquiry.class.getName());
            createCache(cm, com.mycompany.myapp.domain.EnquiryDetails.class.getName());
            createCache(cm, com.mycompany.myapp.domain.EnquiryMaterial.class.getName());
            createCache(cm, com.mycompany.myapp.domain.EnquiryNote.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Favourites.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Localization.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Message.class.getName());
            createCache(cm, com.mycompany.myapp.domain.MessageDeleteDetails.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Notification.class.getName());
            createCache(cm, com.mycompany.myapp.domain.NotificationReceiver.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Offer.class.getName());
            createCache(cm, com.mycompany.myapp.domain.OfferPrice.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Order.class.getName());
            createCache(cm, com.mycompany.myapp.domain.PaymentDetails.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Rating.class.getName());
            createCache(cm, com.mycompany.myapp.domain.StaticPages.class.getName());
            createCache(cm, com.mycompany.myapp.domain.SubscriptionPlan.class.getName());
            createCache(cm, com.mycompany.myapp.domain.SubscriptionPlan.class.getName() + ".subsriptionPlanFeatures");
            createCache(cm, com.mycompany.myapp.domain.SubsriptionPlanFeature.class.getName());
            createCache(cm, com.mycompany.myapp.domain.SubsriptionPlanFeature.class.getName() + ".subscriptionPlans");
            createCache(cm, com.mycompany.myapp.domain.SubsriptionPlanFeature.class.getName() + ".appFeatures");
            createCache(cm, com.mycompany.myapp.domain.SupplierEnquiryMapping.class.getName());
            createCache(cm, com.mycompany.myapp.domain.TradingHours.class.getName());
            createCache(cm, com.mycompany.myapp.domain.TransactionHistory.class.getName());
            createCache(cm, com.mycompany.myapp.domain.UserProfile.class.getName());
            createCache(cm, com.mycompany.myapp.domain.UserProfile.class.getName() + ".userCategoryMappings");
            createCache(cm, com.mycompany.myapp.domain.UserSubscription.class.getName());
            createCache(cm, com.mycompany.myapp.domain.UserCategoryMapping.class.getName());
            createCache(cm, com.mycompany.myapp.domain.UserCategoryMapping.class.getName() + ".userProfiles");
            createCache(cm, com.mycompany.myapp.domain.UserCategoryMapping.class.getName() + ".categories");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }
}
