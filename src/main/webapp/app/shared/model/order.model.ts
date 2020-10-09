import { IContractor } from 'app/shared/model/contractor.model';
import { IForwarder } from 'app/shared/model/forwarder.model';
import { IAddress } from 'app/shared/model/address.model';
import { Currency } from 'app/shared/model/enumerations/currency.model';

export interface IOrder {
  id?: number;
  customerPrice?: number;
  carrierPrice?: number;
  customerCurrency?: Currency;
  carrierCurrency?: Currency;
  customer?: IContractor;
  carrier?: IContractor;
  forwarder?: IForwarder;
  loadingPlace?: IAddress;
  unloadingPlace?: IAddress;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public customerPrice?: number,
    public carrierPrice?: number,
    public customerCurrency?: Currency,
    public carrierCurrency?: Currency,
    public customer?: IContractor,
    public carrier?: IContractor,
    public forwarder?: IForwarder,
    public loadingPlace?: IAddress,
    public unloadingPlace?: IAddress
  ) {}
}
