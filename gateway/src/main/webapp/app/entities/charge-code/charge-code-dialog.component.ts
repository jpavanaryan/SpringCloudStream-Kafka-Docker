import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ChargeCode } from './charge-code.model';
import { ChargeCodePopupService } from './charge-code-popup.service';
import { ChargeCodeService } from './charge-code.service';

@Component({
    selector: 'jhi-charge-code-dialog',
    templateUrl: './charge-code-dialog.component.html'
})
export class ChargeCodeDialogComponent implements OnInit {

    chargeCode: ChargeCode;
    isSaving: boolean;
    chargeCodeStartDateDp: any;
    chargeCodeEndDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private chargeCodeService: ChargeCodeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.chargeCode.id !== undefined) {
            this.subscribeToSaveResponse(
                this.chargeCodeService.update(this.chargeCode));
        } else {
            this.subscribeToSaveResponse(
                this.chargeCodeService.create(this.chargeCode));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ChargeCode>>) {
        result.subscribe((res: HttpResponse<ChargeCode>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ChargeCode) {
        this.eventManager.broadcast({ name: 'chargeCodeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-charge-code-popup',
    template: ''
})
export class ChargeCodePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private chargeCodePopupService: ChargeCodePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.chargeCodePopupService
                    .open(ChargeCodeDialogComponent as Component, params['id']);
            } else {
                this.chargeCodePopupService
                    .open(ChargeCodeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
