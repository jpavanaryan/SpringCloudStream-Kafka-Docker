import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ChargeCodeProjectComponent } from './charge-code-project.component';
import { ChargeCodeProjectDetailComponent } from './charge-code-project-detail.component';
import { ChargeCodeProjectPopupComponent } from './charge-code-project-dialog.component';
import { ChargeCodeProjectDeletePopupComponent } from './charge-code-project-delete-dialog.component';

export const chargeCodeProjectRoute: Routes = [
    {
        path: 'charge-code-project',
        component: ChargeCodeProjectComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChargeCodeProjects'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'charge-code-project/:id',
        component: ChargeCodeProjectDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChargeCodeProjects'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chargeCodeProjectPopupRoute: Routes = [
    {
        path: 'charge-code-project-new',
        component: ChargeCodeProjectPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChargeCodeProjects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'charge-code-project/:id/edit',
        component: ChargeCodeProjectPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChargeCodeProjects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'charge-code-project/:id/delete',
        component: ChargeCodeProjectDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChargeCodeProjects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
