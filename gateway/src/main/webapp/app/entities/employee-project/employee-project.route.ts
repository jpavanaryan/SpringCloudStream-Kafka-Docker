import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EmployeeProjectComponent } from './employee-project.component';
import { EmployeeProjectDetailComponent } from './employee-project-detail.component';
import { EmployeeProjectPopupComponent } from './employee-project-dialog.component';
import { EmployeeProjectDeletePopupComponent } from './employee-project-delete-dialog.component';

export const employeeProjectRoute: Routes = [
    {
        path: 'employee-project',
        component: EmployeeProjectComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeProjects'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'employee-project/:id',
        component: EmployeeProjectDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeProjects'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employeeProjectPopupRoute: Routes = [
    {
        path: 'employee-project-new',
        component: EmployeeProjectPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeProjects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-project/:id/edit',
        component: EmployeeProjectPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeProjects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employee-project/:id/delete',
        component: EmployeeProjectDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmployeeProjects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
