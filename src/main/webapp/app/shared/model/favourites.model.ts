import { Moment } from 'moment';

export interface IFavourites {
  id?: number;
  createdAt?: Moment;
  remarks?: string;
  fromProfileId?: number;
  toProfileId?: number;
}

export class Favourites implements IFavourites {
  constructor(
    public id?: number,
    public createdAt?: Moment,
    public remarks?: string,
    public fromProfileId?: number,
    public toProfileId?: number
  ) {}
}
