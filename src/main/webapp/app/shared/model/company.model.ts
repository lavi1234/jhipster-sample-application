import { Moment } from 'moment';

export interface ICompany {
  id?: number;
  name?: string;
  email?: string;
  termsConditions?: string;
  aboutUs?: string;
  catalogue?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  addressId?: number;
  createdById?: number;
  updatedById?: number;
}

export class Company implements ICompany {
  constructor(
    public id?: number,
    public name?: string,
    public email?: string,
    public termsConditions?: string,
    public aboutUs?: string,
    public catalogue?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public addressId?: number,
    public createdById?: number,
    public updatedById?: number
  ) {}
}
