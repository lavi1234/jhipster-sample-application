import { Moment } from 'moment';

export interface IEnquiry {
  id?: number;
  description?: string;
  deliveryTerms?: string;
  offerTaxtUntil?: Moment;
  status?: string;
  productId?: number;
  deliveryAddressId?: number;
}

export class Enquiry implements IEnquiry {
  constructor(
    public id?: number,
    public description?: string,
    public deliveryTerms?: string,
    public offerTaxtUntil?: Moment,
    public status?: string,
    public productId?: number,
    public deliveryAddressId?: number
  ) {}
}
