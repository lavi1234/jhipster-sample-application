export interface IEnquiryMaterial {
  id?: number;
  name?: string;
  dimension?: string;
  materialId?: number;
  color?: string;
  comments?: string;
  enquiryDetailsId?: number;
}

export class EnquiryMaterial implements IEnquiryMaterial {
  constructor(
    public id?: number,
    public name?: string,
    public dimension?: string,
    public materialId?: number,
    public color?: string,
    public comments?: string,
    public enquiryDetailsId?: number
  ) {}
}
