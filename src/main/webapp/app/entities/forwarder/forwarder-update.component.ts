import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IForwarder, Forwarder } from 'app/shared/model/forwarder.model';
import { ForwarderService } from './forwarder.service';

@Component({
  selector: 'jhi-forwarder-update',
  templateUrl: './forwarder-update.component.html'
})
export class ForwarderUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    email: [],
    phoneNumber: [],
    hireDate: [],
    salary: [],
    branch: []
  });

  constructor(protected forwarderService: ForwarderService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ forwarder }) => {
      this.updateForm(forwarder);
    });
  }

  updateForm(forwarder: IForwarder): void {
    this.editForm.patchValue({
      id: forwarder.id,
      firstName: forwarder.firstName,
      lastName: forwarder.lastName,
      email: forwarder.email,
      phoneNumber: forwarder.phoneNumber,
      hireDate: forwarder.hireDate != null ? forwarder.hireDate.format(DATE_TIME_FORMAT) : null,
      salary: forwarder.salary,
      branch: forwarder.branch
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const forwarder = this.createFromForm();
    if (forwarder.id !== undefined) {
      this.subscribeToSaveResponse(this.forwarderService.update(forwarder));
    } else {
      this.subscribeToSaveResponse(this.forwarderService.create(forwarder));
    }
  }

  private createFromForm(): IForwarder {
    return {
      ...new Forwarder(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      email: this.editForm.get(['email'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      hireDate:
        this.editForm.get(['hireDate'])!.value != null ? moment(this.editForm.get(['hireDate'])!.value, DATE_TIME_FORMAT) : undefined,
      salary: this.editForm.get(['salary'])!.value,
      branch: this.editForm.get(['branch'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IForwarder>>): void {
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
