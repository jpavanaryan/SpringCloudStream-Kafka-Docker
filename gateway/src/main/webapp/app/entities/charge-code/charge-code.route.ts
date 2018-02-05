import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ChargeCodeComponent } from './charge-code.component';
import { ChargeCodeDetailComponent } from './charge-code-detail.component';
import { ChargeCodePopupComponent } from './charge-code-dialog.component';
import { ChargeCodeDeletePopupComponent } from './charge-code-delete-dialog.component';

@Injectable()
export class ChargeCodeResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const chargeCodeRoute: Routes = [
    {
        path: 'charge-code',
        component: ChargeCodeComponent,
        resolve: {
            'pagingParams': ChargeCodeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChargeCodes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'charge-code/:id',
        component: ChargeCodeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChargeCodes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chargeCodePopupRoute: Routes = [
    {
        path: 'charge-code-new',
        component: ChargeCodePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChargeCodes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'charge-code/:id/edit',
        component: ChargeCodePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChargeCodes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'charge-code/:id/delete',
        component: ChargeCodeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChargeCodes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
