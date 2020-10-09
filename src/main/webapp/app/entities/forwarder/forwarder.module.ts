import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrdersManagementSharedModule } from 'app/shared/shared.module';
import { ForwarderComponent } from './forwarder.component';
import { ForwarderDetailComponent } from './forwarder-detail.component';
import { ForwarderUpdateComponent } from './forwarder-update.component';
import { ForwarderDeleteDialogComponent } from './forwarder-delete-dialog.component';
import { forwarderRoute } from './forwarder.route';

@NgModule({
  imports: [OrdersManagementSharedModule, RouterModule.forChild(forwarderRoute)],
  declarations: [ForwarderComponent, ForwarderDetailComponent, ForwarderUpdateComponent, ForwarderDeleteDialogComponent],
  entryComponents: [ForwarderDeleteDialogComponent]
})
export class OrdersManagementForwarderModule {}
