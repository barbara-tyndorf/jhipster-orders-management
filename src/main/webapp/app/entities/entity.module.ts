import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.OrdersManagementAddressModule)
      },
      {
        path: 'contractor',
        loadChildren: () => import('./contractor/contractor.module').then(m => m.OrdersManagementContractorModule)
      },
      {
        path: 'forwarder',
        loadChildren: () => import('./forwarder/forwarder.module').then(m => m.OrdersManagementForwarderModule)
      },
      {
        path: 'order',
        loadChildren: () => import('./order/order.module').then(m => m.OrdersManagementOrderModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class OrdersManagementEntityModule {}
