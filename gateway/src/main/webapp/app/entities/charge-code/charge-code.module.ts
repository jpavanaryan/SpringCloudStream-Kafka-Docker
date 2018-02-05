import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GatewaySharedModule } from '../../shared';
import {
    ChargeCodeService,
    ChargeCodePopupService,
    ChargeCodeComponent,
    ChargeCodeDetailComponent,
    ChargeCodeDialogComponent,
    ChargeCodePopupComponent,
    ChargeCodeDeletePopupComponent,
    ChargeCodeDeleteDialogComponent,
    chargeCodeRoute,
    chargeCodePopupRoute,
    ChargeCodeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...chargeCodeRoute,
    ...chargeCodePopupRoute,
];

@NgModule({
    imports: [
        GatewaySharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ChargeCodeComponent,
        ChargeCodeDetailComponent,
        ChargeCodeDialogComponent,
        ChargeCodeDeleteDialogComponent,
        ChargeCodePopupComponent,
        ChargeCodeDeletePopupComponent,
    ],
    entryComponents: [
        ChargeCodeComponent,
        ChargeCodeDialogComponent,
        ChargeCodePopupComponent,
        ChargeCodeDeleteDialogComponent,
        ChargeCodeDeletePopupComponent,
    ],
    providers: [
        ChargeCodeService,
        ChargeCodePopupService,
        ChargeCodeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GatewayChargeCodeModule {}
