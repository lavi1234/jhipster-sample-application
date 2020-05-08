import { Moment } from 'moment';

export interface INotificationReceiver {
  id?: number;
  readStatus?: boolean;
  updatedAt?: Moment;
  notificationId?: number;
  userProfileId?: number;
}

export class NotificationReceiver implements INotificationReceiver {
  constructor(
    public id?: number,
    public readStatus?: boolean,
    public updatedAt?: Moment,
    public notificationId?: number,
    public userProfileId?: number
  ) {
    this.readStatus = this.readStatus || false;
  }
}
