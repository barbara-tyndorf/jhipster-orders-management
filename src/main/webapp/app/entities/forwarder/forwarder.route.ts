import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IForwarder, Forwarder } from 'app/shared/model/forwarder.model';
import { ForwarderService } from './forwarder.service';
import { ForwarderComponent } from './forwarder.component';
import { ForwarderDetailComponent } from './forwarder-detail.component';
import { ForwarderUpdateComponent } from './forwarder-update.component';

@Injectable({ providedIn: 'root' })
export class ForwarderResolve implements Resolve<IForwarder> {
  constructor(private service: ForwarderService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IForwarder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((forwarder: HttpResponse<Forwarder>) => {
          if (forwarder.body) {
            return of(forwarder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Forwarder());
  }
}

export const forwarderRoute: Routes = [
  {
    path: '',
    component: ForwarderComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ordersManagementApp.forwarder.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ForwarderDetailComponent,
    resolve: {
      forwarder: ForwarderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ordersManagementApp.forwarder.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ForwarderUpdateComponent,
    resolve: {
      forwarder: ForwarderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ordersManagementApp.forwarder.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ForwarderUpdateComponent,
    resolve: {
      forwarder: ForwarderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ordersManagementApp.forwarder.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
