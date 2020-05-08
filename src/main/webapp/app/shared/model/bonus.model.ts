import { Moment } from 'moment';

export interface IBonus {
  id?: number;
  amount?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  principalAmount?: number;
  status?: string;
  remarks?: string;
  buyerId?: number;
  orderId?: number;
}

export class Bonus implements IBonus {
  constructor(
    public id?: number,
    public amount?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public principalAmount?: number,
    public status?: string,
    public remarks?: string,
    public buyerId?: number,
    public orderId?: number
  ) {}
}
