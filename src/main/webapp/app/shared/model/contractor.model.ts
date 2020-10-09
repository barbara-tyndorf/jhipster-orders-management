import { IAddress } from 'app/shared/model/address.model';
import { IOrder } from 'app/shared/model/order.model';

export interface IContractor {
  id?: number;
  name?: string;
  vatId?: string;
  contactPerson?: string;
  phoneNumber?: string;
  address?: IAddress;
  orders?: IOrder[];
  orders?: IOrder[];
}

export class Contractor implements IContractor {
  constructor(
    public id?: number,
    public name?: string,
    public vatId?: string,
    public contactPerson?: string,
    public phoneNumber?: string,
    public address?: IAddress,
    public orders?: IOrder[],
    public orders?: IOrder[]
  ) {}
}
