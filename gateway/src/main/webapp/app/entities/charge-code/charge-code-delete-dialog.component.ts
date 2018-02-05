import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ChargeCode } from './charge-code.model';
import { ChargeCodePopupService } from './charge-code-popup.service';
import { ChargeCodeService } from './charge-code.service';

@Component({
    selector: 'jhi-charge-code-delete-dialog',
    templateUrl: './charge-code-delete-dialog.component.html'
})
export class ChargeCodeDeleteDialogComponent {

    chargeCode: ChargeCode;

    constructor(
        private chargeCodeService: ChargeCodeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.chargeCodeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'chargeCodeListModification',
                content: 'Deleted an chargeCode'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-charge-code-delete-popup',
    template: ''
})
export class ChargeCodeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private chargeCodePopupService: ChargeCodePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.chargeCodePopupService
                .open(ChargeCodeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
