import { Moment } from 'moment';

export interface IOrder {
  id?: number;
  price?: number;
  deliveryDate?: Moment;
  status?: string;
  commissionDate?: Moment;
  createdAt?: Moment;
  updatedAt?: Moment;
  remarks?: string;
  offerId?: number;
  buyerId?: number;
  supplierId?: number;
  enquiryId?: number;
  enquiryDetailsId?: number;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public price?: number,
    public deliveryDate?: Moment,
    public status?: string,
    public commissionDate?: Moment,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public remarks?: string,
    public offerId?: number,
    public buyerId?: number,
    public supplierId?: number,
    public enquiryId?: number,
    public enquiryDetailsId?: number
  ) {}
}
