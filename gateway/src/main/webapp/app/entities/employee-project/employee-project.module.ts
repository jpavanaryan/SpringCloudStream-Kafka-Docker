import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    EmployeeProjectService,
    EmployeeProjectPopupService,
    EmployeeProjectComponent,
    EmployeeProjectDetailComponent,
    EmployeeProjectDialogComponent,
    EmployeeProjectPopupComponent,
    EmployeeProjectDeletePopupComponent,
    EmployeeProjectDeleteDialogComponent,
    employeeProjectRoute,
    employeeProjectPopupRoute,
} from './';

const ENTITY_STATES = [
    ...employeeProjectRoute,
    ...employeeProjectPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EmployeeProjectComponent,
        EmployeeProjectDetailComponent,
        EmployeeProjectDialogComponent,
        EmployeeProjectDeleteDialogComponent,
        EmployeeProjectPopupComponent,
        EmployeeProjectDeletePopupComponent,
    ],
    entryComponents: [
        EmployeeProjectComponent,
        EmployeeProjectDialogComponent,
        EmployeeProjectPopupComponent,
        EmployeeProjectDeleteDialogComponent,
        EmployeeProjectDeletePopupComponent,
    ],
    providers: [
        EmployeeProjectService,
        EmployeeProjectPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayEmployeeProjectModule {}
