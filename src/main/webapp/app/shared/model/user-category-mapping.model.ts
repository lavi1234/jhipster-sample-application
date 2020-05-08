import { IUserProfile } from 'app/shared/model/user-profile.model';
import { ICategory } from 'app/shared/model/category.model';

export interface IUserCategoryMapping {
  id?: number;
  userProfiles?: IUserProfile[];
  categories?: ICategory[];
}

export class UserCategoryMapping implements IUserCategoryMapping {
  constructor(public id?: number, public userProfiles?: IUserProfile[], public categories?: ICategory[]) {}
}
