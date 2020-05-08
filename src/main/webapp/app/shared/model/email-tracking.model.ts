import { Moment } from 'moment';

export interface IEmailTracking {
  id?: number;
  toEmail?: string;
  subject?: string;
  message?: string;
  type?: string;
  createdAt?: Moment;
  receiverId?: number;
  createdById?: number;
}

export class EmailTracking implements IEmailTracking {
  constructor(
    public id?: number,
    public toEmail?: string,
    public subject?: string,
    public message?: string,
    public type?: string,
    public createdAt?: Moment,
    public receiverId?: number,
    public createdById?: number
  ) {}
}
