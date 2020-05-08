import { Moment } from 'moment';

export interface ISupplierEnquiryMapping {
  id?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  enquiryId?: number;
  supplierId?: number;
  createdById?: number;
}

export class SupplierEnquiryMapping implements ISupplierEnquiryMapping {
  constructor(
    public id?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public enquiryId?: number,
    public supplierId?: number,
    public createdById?: number
  ) {}
}
