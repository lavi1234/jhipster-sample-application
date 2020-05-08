import { Moment } from 'moment';

export interface ITransactionHistory {
  id?: number;
  price?: number;
  createdAt?: Moment;
  paymentGatewayToken?: string;
  paymentGatewayResponse?: string;
  status?: string;
  subscriptionPlanId?: number;
  userProfileId?: number;
}

export class TransactionHistory implements ITransactionHistory {
  constructor(
    public id?: number,
    public price?: number,
    public createdAt?: Moment,
    public paymentGatewayToken?: string,
    public paymentGatewayResponse?: string,
    public status?: string,
    public subscriptionPlanId?: number,
    public userProfileId?: number
  ) {}
}
