import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContractor } from 'app/shared/model/contractor.model';
import { ContractorService } from './contractor.service';
import { ContractorDeleteDialogComponent } from './contractor-delete-dialog.component';

@Component({
  selector: 'jhi-contractor',
  templateUrl: './contractor.component.html'
})
export class ContractorComponent implements OnInit, OnDestroy {
  contractors?: IContractor[];
  eventSubscriber?: Subscription;

  constructor(protected contractorService: ContractorService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.contractorService.query().subscribe((res: HttpResponse<IContractor[]>) => {
      this.contractors = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInContractors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContractor): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInContractors(): void {
    this.eventSubscriber = this.eventManager.subscribe('contractorListModification', () => this.loadAll());
  }

  delete(contractor: IContractor): void {
    const modalRef = this.modalService.open(ContractorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contractor = contractor;
  }
}
