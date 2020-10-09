import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IForwarder } from 'app/shared/model/forwarder.model';
import { ForwarderService } from './forwarder.service';
import { ForwarderDeleteDialogComponent } from './forwarder-delete-dialog.component';

@Component({
  selector: 'jhi-forwarder',
  templateUrl: './forwarder.component.html'
})
export class ForwarderComponent implements OnInit, OnDestroy {
  forwarders?: IForwarder[];
  eventSubscriber?: Subscription;

  constructor(protected forwarderService: ForwarderService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.forwarderService.query().subscribe((res: HttpResponse<IForwarder[]>) => {
      this.forwarders = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInForwarders();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IForwarder): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInForwarders(): void {
    this.eventSubscriber = this.eventManager.subscribe('forwarderListModification', () => this.loadAll());
  }

  delete(forwarder: IForwarder): void {
    const modalRef = this.modalService.open(ForwarderDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.forwarder = forwarder;
  }
}
