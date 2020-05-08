import { Moment } from 'moment';

export interface IBonusReference {
  id?: number;
  percentage?: number;
  holdDays?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
}

export class BonusReference implements IBonusReference {
  constructor(
    public id?: number,
    public percentage?: number,
    public holdDays?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment
  ) {}
}
