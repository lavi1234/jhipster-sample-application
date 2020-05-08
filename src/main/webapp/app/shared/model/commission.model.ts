import { Moment } from 'moment';

export interface ICommission {
  id?: number;
  amount?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  principalAmount?: number;
  status?: string;
  remarks?: string;
  supplierId?: number;
  orderId?: number;
}

export class Commission implements ICommission {
  constructor(
    public id?: number,
    public amount?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public principalAmount?: number,
    public status?: string,
    public remarks?: string,
    public supplierId?: number,
    public orderId?: number
  ) {}
}
