import { Moment } from 'moment';

export interface ICart {
  id?: number;
  createdAt?: Moment;
  enquiryId?: number;
  supplierId?: number;
  createdById?: number;
}

export class Cart implements ICart {
  constructor(
    public id?: number,
    public createdAt?: Moment,
    public enquiryId?: number,
    public supplierId?: number,
    public createdById?: number
  ) {}
}
