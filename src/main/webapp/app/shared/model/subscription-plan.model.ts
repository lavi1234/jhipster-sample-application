import { Moment } from 'moment';
import { ISubsriptionPlanFeature } from 'app/shared/model/subsription-plan-feature.model';

export interface ISubscriptionPlan {
  id?: number;
  validity?: string;
  price?: number;
  createdBy?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  nameId?: number;
  descriptionId?: number;
  subsriptionPlanFeatures?: ISubsriptionPlanFeature[];
}

export class SubscriptionPlan implements ISubscriptionPlan {
  constructor(
    public id?: number,
    public validity?: string,
    public price?: number,
    public createdBy?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public nameId?: number,
    public descriptionId?: number,
    public subsriptionPlanFeatures?: ISubsriptionPlanFeature[]
  ) {}
}
