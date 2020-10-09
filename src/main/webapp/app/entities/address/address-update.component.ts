import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAddress, Address } from 'app/shared/model/address.model';
import { AddressService } from './address.service';

@Component({
  selector: 'jhi-address-update',
  templateUrl: './address-update.component.html'
})
export class AddressUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    street: [],
    number: [],
    zip: [],
    city: [],
    countryCode: []
  });

  constructor(protected addressService: AddressService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ address }) => {
      this.updateForm(address);
    });
  }

  updateForm(address: IAddress): void {
    this.editForm.patchValue({
      id: address.id,
      name: address.name,
      street: address.street,
      number: address.number,
      zip: address.zip,
      city: address.city,
      countryCode: address.countryCode
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const address = this.createFromForm();
    if (address.id !== undefined) {
      this.subscribeToSaveResponse(this.addressService.update(address));
    } else {
      this.subscribeToSaveResponse(this.addressService.create(address));
    }
  }

  private createFromForm(): IAddress {
    return {
      ...new Address(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      street: this.editForm.get(['street'])!.value,
      number: this.editForm.get(['number'])!.value,
      zip: this.editForm.get(['zip'])!.value,
      city: this.editForm.get(['city'])!.value,
      countryCode: this.editForm.get(['countryCode'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddress>>): void {
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
}
