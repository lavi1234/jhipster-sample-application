import { Moment } from 'moment';

export interface IOffer {
  id?: number;
  validUpto?: Moment;
  status?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  supplierEnquiryId?: number;
  createdById?: number;
  updatedById?: number;
}

export class Offer implements IOffer {
  constructor(
    public id?: number,
    public validUpto?: Moment,
    public status?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public supplierEnquiryId?: number,
    public createdById?: number,
    public updatedById?: number
  ) {}
}
