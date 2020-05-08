import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.JhipsterSampleApplicationAddressModule)
      },
      {
        path: 'app-features',
        loadChildren: () => import('./app-features/app-features.module').then(m => m.JhipsterSampleApplicationAppFeaturesModule)
      },
      {
        path: 'bonus',
        loadChildren: () => import('./bonus/bonus.module').then(m => m.JhipsterSampleApplicationBonusModule)
      },
      {
        path: 'bonus-reference',
        loadChildren: () => import('./bonus-reference/bonus-reference.module').then(m => m.JhipsterSampleApplicationBonusReferenceModule)
      },
      {
        path: 'cart',
        loadChildren: () => import('./cart/cart.module').then(m => m.JhipsterSampleApplicationCartModule)
      },
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.JhipsterSampleApplicationCategoryModule)
      },
      {
        path: 'commission',
        loadChildren: () => import('./commission/commission.module').then(m => m.JhipsterSampleApplicationCommissionModule)
      },
      {
        path: 'commission-reference',
        loadChildren: () =>
          import('./commission-reference/commission-reference.module').then(m => m.JhipsterSampleApplicationCommissionReferenceModule)
      },
      {
        path: 'company',
        loadChildren: () => import('./company/company.module').then(m => m.JhipsterSampleApplicationCompanyModule)
      },
      {
        path: 'email-tracking',
        loadChildren: () => import('./email-tracking/email-tracking.module').then(m => m.JhipsterSampleApplicationEmailTrackingModule)
      },
      {
        path: 'enquiry',
        loadChildren: () => import('./enquiry/enquiry.module').then(m => m.JhipsterSampleApplicationEnquiryModule)
      },
      {
        path: 'enquiry-details',
        loadChildren: () => import('./enquiry-details/enquiry-details.module').then(m => m.JhipsterSampleApplicationEnquiryDetailsModule)
      },
      {
        path: 'enquiry-material',
        loadChildren: () => import('./enquiry-material/enquiry-material.module').then(m => m.JhipsterSampleApplicationEnquiryMaterialModule)
      },
      {
        path: 'enquiry-note',
        loadChildren: () => import('./enquiry-note/enquiry-note.module').then(m => m.JhipsterSampleApplicationEnquiryNoteModule)
      },
      {
        path: 'favourites',
        loadChildren: () => import('./favourites/favourites.module').then(m => m.JhipsterSampleApplicationFavouritesModule)
      },
      {
        path: 'localization',
        loadChildren: () => import('./localization/localization.module').then(m => m.JhipsterSampleApplicationLocalizationModule)
      },
      {
        path: 'message',
        loadChildren: () => import('./message/message.module').then(m => m.JhipsterSampleApplicationMessageModule)
      },
      {
        path: 'message-delete-details',
        loadChildren: () =>
          import('./message-delete-details/message-delete-details.module').then(m => m.JhipsterSampleApplicationMessageDeleteDetailsModule)
      },
      {
        path: 'notification',
        loadChildren: () => import('./notification/notification.module').then(m => m.JhipsterSampleApplicationNotificationModule)
      },
      {
        path: 'notification-receiver',
        loadChildren: () =>
          import('./notification-receiver/notification-receiver.module').then(m => m.JhipsterSampleApplicationNotificationReceiverModule)
      },
      {
        path: 'offer',
        loadChildren: () => import('./offer/offer.module').then(m => m.JhipsterSampleApplicationOfferModule)
      },
      {
        path: 'offer-price',
        loadChildren: () => import('./offer-price/offer-price.module').then(m => m.JhipsterSampleApplicationOfferPriceModule)
      },
      {
        path: 'order',
        loadChildren: () => import('./order/order.module').then(m => m.JhipsterSampleApplicationOrderModule)
      },
      {
        path: 'payment-details',
        loadChildren: () => import('./payment-details/payment-details.module').then(m => m.JhipsterSampleApplicationPaymentDetailsModule)
      },
      {
        path: 'rating',
        loadChildren: () => import('./rating/rating.module').then(m => m.JhipsterSampleApplicationRatingModule)
      },
      {
        path: 'static-pages',
        loadChildren: () => import('./static-pages/static-pages.module').then(m => m.JhipsterSampleApplicationStaticPagesModule)
      },
      {
        path: 'subscription-plan',
        loadChildren: () =>
          import('./subscription-plan/subscription-plan.module').then(m => m.JhipsterSampleApplicationSubscriptionPlanModule)
      },
      {
        path: 'subsription-plan-feature',
        loadChildren: () =>
          import('./subsription-plan-feature/subsription-plan-feature.module').then(
            m => m.JhipsterSampleApplicationSubsriptionPlanFeatureModule
          )
      },
      {
        path: 'supplier-enquiry-mapping',
        loadChildren: () =>
          import('./supplier-enquiry-mapping/supplier-enquiry-mapping.module').then(
            m => m.JhipsterSampleApplicationSupplierEnquiryMappingModule
          )
      },
      {
        path: 'trading-hours',
        loadChildren: () => import('./trading-hours/trading-hours.module').then(m => m.JhipsterSampleApplicationTradingHoursModule)
      },
      {
        path: 'transaction-history',
        loadChildren: () =>
          import('./transaction-history/transaction-history.module').then(m => m.JhipsterSampleApplicationTransactionHistoryModule)
      },
      {
        path: 'user-profile',
        loadChildren: () => import('./user-profile/user-profile.module').then(m => m.JhipsterSampleApplicationUserProfileModule)
      },
      {
        path: 'user-subscription',
        loadChildren: () =>
          import('./user-subscription/user-subscription.module').then(m => m.JhipsterSampleApplicationUserSubscriptionModule)
      },
      {
        path: 'user-category-mapping',
        loadChildren: () =>
          import('./user-category-mapping/user-category-mapping.module').then(m => m.JhipsterSampleApplicationUserCategoryMappingModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JhipsterSampleApplicationEntityModule {}
