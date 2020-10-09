import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContractor, Contractor } from 'app/shared/model/contractor.model';
import { ContractorService } from './contractor.service';
import { ContractorComponent } from './contractor.component';
import { ContractorDetailComponent } from './contractor-detail.component';
import { ContractorUpdateComponent } from './contractor-update.component';

@Injectable({ providedIn: 'root' })
export class ContractorResolve implements Resolve<IContractor> {
  constructor(private service: ContractorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContractor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contractor: HttpResponse<Contractor>) => {
          if (contractor.body) {
            return of(contractor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Contractor());
  }
}

export const contractorRoute: Routes = [
  {
    path: '',
    component: ContractorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ordersManagementApp.contractor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContractorDetailComponent,
    resolve: {
      contractor: ContractorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ordersManagementApp.contractor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContractorUpdateComponent,
    resolve: {
      contractor: ContractorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ordersManagementApp.contractor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContractorUpdateComponent,
    resolve: {
      contractor: ContractorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ordersManagementApp.contractor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
