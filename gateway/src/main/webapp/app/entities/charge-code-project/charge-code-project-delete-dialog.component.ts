import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ChargeCodeProject } from './charge-code-project.model';
import { ChargeCodeProjectPopupService } from './charge-code-project-popup.service';
import { ChargeCodeProjectService } from './charge-code-project.service';

@Component({
    selector: 'jhi-charge-code-project-delete-dialog',
    templateUrl: './charge-code-project-delete-dialog.component.html'
})
export class ChargeCodeProjectDeleteDialogComponent {

    chargeCodeProject: ChargeCodeProject;

    constructor(
        private chargeCodeProjectService: ChargeCodeProjectService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.chargeCodeProjectService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'chargeCodeProjectListModification',
                content: 'Deleted an chargeCodeProject'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-charge-code-project-delete-popup',
    template: ''
})
export class ChargeCodeProjectDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private chargeCodeProjectPopupService: ChargeCodeProjectPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.chargeCodeProjectPopupService
                .open(ChargeCodeProjectDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
