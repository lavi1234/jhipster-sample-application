import { Moment } from 'moment';

export interface IRating {
  id?: number;
  rating?: number;
  createdAt?: Moment;
  remarks?: string;
  fromProfileId?: number;
  toProfileId?: number;
  orderId?: number;
}

export class Rating implements IRating {
  constructor(
    public id?: number,
    public rating?: number,
    public createdAt?: Moment,
    public remarks?: string,
    public fromProfileId?: number,
    public toProfileId?: number,
    public orderId?: number
  ) {}
}
