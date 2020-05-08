import { Moment } from 'moment';
import { IUserCategoryMapping } from 'app/shared/model/user-category-mapping.model';

export interface ICategory {
  id?: number;
  createdBy?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  nameId?: number;
  descriptionId?: number;
  parentId?: number;
  userCategoryMappings?: IUserCategoryMapping[];
}

export class Category implements ICategory {
  constructor(
    public id?: number,
    public createdBy?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public nameId?: number,
    public descriptionId?: number,
    public parentId?: number,
    public userCategoryMappings?: IUserCategoryMapping[]
  ) {}
}
