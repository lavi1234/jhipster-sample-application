import { Moment } from 'moment';

export interface IEnquiryDetails {
  id?: number;
  version?: number;
  edition?: number;
  format?: number;
  documents?: string;
  deliveryDate?: Moment;
  remarks?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  enquiryId?: number;
  printId?: number;
  finishingId?: number;
  handlingId?: number;
  packagingId?: number;
  createdById?: number;
  offerId?: number;
}

export class EnquiryDetails implements IEnquiryDetails {
  constructor(
    public id?: number,
    public version?: number,
    public edition?: number,
    public format?: number,
    public documents?: string,
    public deliveryDate?: Moment,
    public remarks?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public enquiryId?: number,
    public printId?: number,
    public finishingId?: number,
    public handlingId?: number,
    public packagingId?: number,
    public createdById?: number,
    public offerId?: number
  ) {}
}
