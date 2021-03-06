import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrdersManagementSharedModule } from 'app/shared/shared.module';
import { ContractorComponent } from './contractor.component';
import { ContractorDetailComponent } from './contractor-detail.component';
import { ContractorUpdateComponent } from './contractor-update.component';
import { ContractorDeleteDialogComponent } from './contractor-delete-dialog.component';
import { contractorRoute } from './contractor.route';

@NgModule({
  imports: [OrdersManagementSharedModule, RouterModule.forChild(contractorRoute)],
  declarations: [ContractorComponent, ContractorDetailComponent, ContractorUpdateComponent, ContractorDeleteDialogComponent],
  entryComponents: [ContractorDeleteDialogComponent]
})
export class OrdersManagementContractorModule {}
