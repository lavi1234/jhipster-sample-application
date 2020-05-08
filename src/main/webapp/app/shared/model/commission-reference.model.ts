import { Moment } from 'moment';

export interface ICommissionReference {
  id?: number;
  percentage?: number;
  holdDays?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
}

export class CommissionReference implements ICommissionReference {
  constructor(
    public id?: number,
    public percentage?: number,
    public holdDays?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment
  ) {}
}
