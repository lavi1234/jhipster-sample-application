import { Moment } from 'moment';

export interface IMessage {
  id?: number;
  subject?: string;
  message?: string;
  createdAt?: Moment;
  apiResponse?: string;
  discussionType?: string;
  readStatus?: boolean;
  fromId?: number;
  toId?: number;
  createdById?: number;
  enquiryId?: number;
  orderId?: number;
  offerId?: number;
  replyMessageId?: number;
}

export class Message implements IMessage {
  constructor(
    public id?: number,
    public subject?: string,
    public message?: string,
    public createdAt?: Moment,
    public apiResponse?: string,
    public discussionType?: string,
    public readStatus?: boolean,
    public fromId?: number,
    public toId?: number,
    public createdById?: number,
    public enquiryId?: number,
    public orderId?: number,
    public offerId?: number,
    public replyMessageId?: number
  ) {
    this.readStatus = this.readStatus || false;
  }
}
