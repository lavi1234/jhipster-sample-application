import { Moment } from 'moment';

export interface IUserSubscription {
  id?: number;
  price?: number;
  validUpto?: Moment;
  paymentGatewayToken?: string;
  nextRenewal?: Moment;
  createdAt?: Moment;
  updatedAt?: Moment;
  subscriptionPlanId?: number;
  userProfileId?: number;
}

export class UserSubscription implements IUserSubscription {
  constructor(
    public id?: number,
    public price?: number,
    public validUpto?: Moment,
    public paymentGatewayToken?: string,
    public nextRenewal?: Moment,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public subscriptionPlanId?: number,
    public userProfileId?: number
  ) {}
}
