import { Moment } from 'moment';
import { IOrder } from 'app/shared/model/order.model';
import { Branch } from 'app/shared/model/enumerations/branch.model';

export interface IForwarder {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string;
  hireDate?: Moment;
  salary?: number;
  branch?: Branch;
  orders?: IOrder[];
}

export class Forwarder implements IForwarder {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public phoneNumber?: string,
    public hireDate?: Moment,
    public salary?: number,
    public branch?: Branch,
    public orders?: IOrder[]
  ) {}
}
