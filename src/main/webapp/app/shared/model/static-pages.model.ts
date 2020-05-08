import { Moment } from 'moment';

export interface IStaticPages {
  id?: number;
  pageTitle?: string;
  heading?: string;
  description?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  publish?: boolean;
}

export class StaticPages implements IStaticPages {
  constructor(
    public id?: number,
    public pageTitle?: string,
    public heading?: string,
    public description?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public publish?: boolean
  ) {
    this.publish = this.publish || false;
  }
}
