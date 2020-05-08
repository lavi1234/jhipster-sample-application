import { ISubsriptionPlanFeature } from 'app/shared/model/subsription-plan-feature.model';

export interface IAppFeatures {
  id?: number;
  menuSortNumber?: number;
  nameId?: number;
  subsriptionPlanFeatures?: ISubsriptionPlanFeature[];
}

export class AppFeatures implements IAppFeatures {
  constructor(
    public id?: number,
    public menuSortNumber?: number,
    public nameId?: number,
    public subsriptionPlanFeatures?: ISubsriptionPlanFeature[]
  ) {}
}
