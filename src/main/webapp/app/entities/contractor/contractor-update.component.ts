import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IContractor, Contractor } from 'app/shared/model/contractor.model';
import { ContractorService } from './contractor.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';

@Component({
  selector: 'jhi-contractor-update',
  templateUrl: './contractor-update.component.html'
})
export class ContractorUpdateComponent implements OnInit {
  isSaving = false;

  addresses: IAddress[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    vatId: [],
    contactPerson: [],
    phoneNumber: [],
    address: []
  });

  constructor(
    protected contractorService: ContractorService,
    protected addressService: AddressService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contractor }) => {
      this.updateForm(contractor);

      this.addressService
        .query({ filter: 'contractor-is-null' })
        .pipe(
          map((res: HttpResponse<IAddress[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IAddress[]) => {
          if (!contractor.address || !contractor.address.id) {
            this.addresses = resBody;
          } else {
            this.addressService
              .find(contractor.address.id)
              .pipe(
                map((subRes: HttpResponse<IAddress>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAddress[]) => {
                this.addresses = concatRes;
              });
          }
        });
    });
  }

  updateForm(contractor: IContractor): void {
    this.editForm.patchValue({
      id: contractor.id,
      name: contractor.name,
      vatId: contractor.vatId,
      contactPerson: contractor.contactPerson,
      phoneNumber: contractor.phoneNumber,
      address: contractor.address
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contractor = this.createFromForm();
    if (contractor.id !== undefined) {
      this.subscribeToSaveResponse(this.contractorService.update(contractor));
    } else {
      this.subscribeToSaveResponse(this.contractorService.create(contractor));
    }
  }

  private createFromForm(): IContractor {
    return {
      ...new Contractor(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      vatId: this.editForm.get(['vatId'])!.value,
      contactPerson: this.editForm.get(['contactPerson'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      address: this.editForm.get(['address'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContractor>>): void {
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

  trackById(index: number, item: IAddress): any {
    return item.id;
  }
}
