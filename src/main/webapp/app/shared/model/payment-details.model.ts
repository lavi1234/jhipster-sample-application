import { Moment } from 'moment';

export interface IPaymentDetails {
  id?: number;
  bankName?: string;
  accountNumber?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  userProfileId?: number;
}

export class PaymentDetails implements IPaymentDetails {
  constructor(
    public id?: number,
    public bankName?: string,
    public accountNumber?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public userProfileId?: number
  ) {}
}
