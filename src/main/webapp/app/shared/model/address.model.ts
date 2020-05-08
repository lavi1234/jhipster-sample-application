export interface IAddress {
  id?: number;
  street?: string;
  city?: string;
  state?: string;
  country?: string;
  postalCode?: string;
  geoLatitude?: number;
  geoLongitude?: number;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public street?: string,
    public city?: string,
    public state?: string,
    public country?: string,
    public postalCode?: string,
    public geoLatitude?: number,
    public geoLongitude?: number
  ) {}
}
