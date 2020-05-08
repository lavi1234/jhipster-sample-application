export interface ILocalization {
  id?: number;
  labelEn?: string;
  labelDe?: string;
}

export class Localization implements ILocalization {
  constructor(public id?: number, public labelEn?: string, public labelDe?: string) {}
}
