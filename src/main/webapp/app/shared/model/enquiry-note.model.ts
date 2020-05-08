import { Moment } from 'moment';

export interface IEnquiryNote {
  id?: number;
  note?: string;
  createdAt?: Moment;
  enquiryDetailsId?: number;
}

export class EnquiryNote implements IEnquiryNote {
  constructor(public id?: number, public note?: string, public createdAt?: Moment, public enquiryDetailsId?: number) {}
}
