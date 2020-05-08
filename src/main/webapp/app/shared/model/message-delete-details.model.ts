import { Moment } from 'moment';

export interface IMessageDeleteDetails {
  id?: number;
  deletedAt?: Moment;
  messageId?: number;
  deletedById?: number;
}

export class MessageDeleteDetails implements IMessageDeleteDetails {
  constructor(public id?: number, public deletedAt?: Moment, public messageId?: number, public deletedById?: number) {}
}
