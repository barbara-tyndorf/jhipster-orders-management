import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IOrder, Order } from 'app/shared/model/order.model';
import { OrderService } from './order.service';
import { IContractor } from 'app/shared/model/contractor.model';
import { ContractorService } from 'app/entities/contractor/contractor.service';
import { IForwarder } from 'app/shared/model/forwarder.model';
import { ForwarderService } from 'app/entities/forwarder/forwarder.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';

type SelectableEntity = IContractor | IForwarder | IAddress;

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html'
})
export class OrderUpdateComponent implements OnInit {
  isSaving = false;

  contractors: IContractor[] = [];

  forwarders: IForwarder[] = [];

  addresses: IAddress[] = [];

  editForm = this.fb.group({
    id: [],
    customerPrice: [],
    carrierPrice: [],
    customerCurrency: [],
    carrierCurrency: [],
    customer: [],
    carrier: [],
    forwarder: [],
    loadingPlace: [],
    unloadingPlace: []
  });

  constructor(
    protected orderService: OrderService,
    protected contractorService: ContractorService,
    protected forwarderService: ForwarderService,
    protected addressService: AddressService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ order }) => {
      this.updateForm(order);

      this.contractorService
        .query()
        .pipe(
          map((res: HttpResponse<IContractor[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IContractor[]) => (this.contractors = resBody));

      this.forwarderService
        .query()
        .pipe(
          map((res: HttpResponse<IForwarder[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IForwarder[]) => (this.forwarders = resBody));

      this.addressService
        .query()
        .pipe(
          map((res: HttpResponse<IAddress[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAddress[]) => (this.addresses = resBody));
    });
  }

  updateForm(order: IOrder): void {
    this.editForm.patchValue({
      id: order.id,
      customerPrice: order.customerPrice,
      carrierPrice: order.carrierPrice,
      customerCurrency: order.customerCurrency,
      carrierCurrency: order.carrierCurrency,
      customer: order.customer,
      carrier: order.carrier,
      forwarder: order.forwarder,
      loadingPlace: order.loadingPlace,
      unloadingPlace: order.unloadingPlace
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const order = this.createFromForm();
    if (order.id !== undefined) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  private createFromForm(): IOrder {
    return {
      ...new Order(),
      id: this.editForm.get(['id'])!.value,
      customerPrice: this.editForm.get(['customerPrice'])!.value,
      carrierPrice: this.editForm.get(['carrierPrice'])!.value,
      customerCurrency: this.editForm.get(['customerCurrency'])!.value,
      carrierCurrency: this.editForm.get(['carrierCurrency'])!.value,
      customer: this.editForm.get(['customer'])!.value,
      carrier: this.editForm.get(['carrier'])!.value,
      forwarder: this.editForm.get(['forwarder'])!.value,
      loadingPlace: this.editForm.get(['loadingPlace'])!.value,
      unloadingPlace: this.editForm.get(['unloadingPlace'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
