import { ISubscriptionPlan } from 'app/shared/model/subscription-plan.model';
import { IAppFeatures } from 'app/shared/model/app-features.model';

export interface ISubsriptionPlanFeature {
  id?: number;
  subscriptionPlans?: ISubscriptionPlan[];
  appFeatures?: IAppFeatures[];
}

export class SubsriptionPlanFeature implements ISubsriptionPlanFeature {
  constructor(public id?: number, public subscriptionPlans?: ISubscriptionPlan[], public appFeatures?: IAppFeatures[]) {}
}
