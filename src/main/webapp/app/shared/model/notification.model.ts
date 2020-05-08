import { Moment } from 'moment';

export interface INotification {
  id?: number;
  title?: string;
  description?: string;
  bannerImage?: string;
  createdAt?: Moment;
  createdById?: number;
}

export class Notification implements INotification {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string,
    public bannerImage?: string,
    public createdAt?: Moment,
    public createdById?: number
  ) {}
}
