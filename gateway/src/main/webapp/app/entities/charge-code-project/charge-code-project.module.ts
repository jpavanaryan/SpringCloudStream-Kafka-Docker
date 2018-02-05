import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    ChargeCodeProjectService,
    ChargeCodeProjectPopupService,
    ChargeCodeProjectComponent,
    ChargeCodeProjectDetailComponent,
    ChargeCodeProjectDialogComponent,
    ChargeCodeProjectPopupComponent,
    ChargeCodeProjectDeletePopupComponent,
    ChargeCodeProjectDeleteDialogComponent,
    chargeCodeProjectRoute,
    chargeCodeProjectPopupRoute,
} from './';

const ENTITY_STATES = [
    ...chargeCodeProjectRoute,
    ...chargeCodeProjectPopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ChargeCodeProjectComponent,
        ChargeCodeProjectDetailComponent,
        ChargeCodeProjectDialogComponent,
        ChargeCodeProjectDeleteDialogComponent,
        ChargeCodeProjectPopupComponent,
        ChargeCodeProjectDeletePopupComponent,
    ],
    entryComponents: [
        ChargeCodeProjectComponent,
        ChargeCodeProjectDialogComponent,
        ChargeCodeProjectPopupComponent,
        ChargeCodeProjectDeleteDialogComponent,
        ChargeCodeProjectDeletePopupComponent,
    ],
    providers: [
        ChargeCodeProjectService,
        ChargeCodeProjectPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayChargeCodeProjectModule {}
