import { IOrder } from 'app/shared/model/order.model';

export interface IAddress {
  id?: number;
  name?: string;
  street?: string;
  number?: number;
  zip?: string;
  city?: string;
  countryCode?: string;
  orders?: IOrder[];
  orders?: IOrder[];
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public name?: string,
    public street?: string,
    public number?: number,
    public zip?: string,
    public city?: string,
    public countryCode?: string,
    public orders?: IOrder[],
    public orders?: IOrder[]
  ) {}
}
