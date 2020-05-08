import { Moment } from 'moment';
import { IUserCategoryMapping } from 'app/shared/model/user-category-mapping.model';

export interface IUserProfile {
  id?: number;
  salutation?: string;
  firstName?: string;
  lastName?: string;
  profilePicture?: string;
  phoneNumber?: string;
  userType?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  userId?: number;
  companyId?: number;
  userCategoryMappings?: IUserCategoryMapping[];
}

export class UserProfile implements IUserProfile {
  constructor(
    public id?: number,
    public salutation?: string,
    public firstName?: string,
    public lastName?: string,
    public profilePicture?: string,
    public phoneNumber?: string,
    public userType?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public userId?: number,
    public companyId?: number,
    public userCategoryMappings?: IUserCategoryMapping[]
  ) {}
}
