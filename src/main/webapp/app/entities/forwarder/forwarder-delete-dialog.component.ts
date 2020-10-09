import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IForwarder } from 'app/shared/model/forwarder.model';
import { ForwarderService } from './forwarder.service';

@Component({
  templateUrl: './forwarder-delete-dialog.component.html'
})
export class ForwarderDeleteDialogComponent {
  forwarder?: IForwarder;

  constructor(protected forwarderService: ForwarderService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.forwarderService.delete(id).subscribe(() => {
      this.eventManager.broadcast('forwarderListModification');
      this.activeModal.close();
    });
  }
}
