import { Moment } from 'moment';

export interface IOfferPrice {
  id?: number;
  price?: number;
  createdAt?: Moment;
  finishingDate?: Moment;
  offerId?: number;
  enquiryId?: number;
  enquiryDetailsId?: number;
}

export class OfferPrice implements IOfferPrice {
  constructor(
    public id?: number,
    public price?: number,
    public createdAt?: Moment,
    public finishingDate?: Moment,
    public offerId?: number,
    public enquiryId?: number,
    public enquiryDetailsId?: number
  ) {}
}
