export interface ITradingHours {
  id?: number;
  day?: string;
  startTime?: string;
  endTime?: string;
  companyId?: number;
}

export class TradingHours implements ITradingHours {
  constructor(public id?: number, public day?: string, public startTime?: string, public endTime?: string, public companyId?: number) {}
}
