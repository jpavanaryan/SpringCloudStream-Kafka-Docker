import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ChargeCodeProject } from './charge-code-project.model';
import { ChargeCodeProjectPopupService } from './charge-code-project-popup.service';
import { ChargeCodeProjectService } from './charge-code-project.service';
import { ChargeCode, ChargeCodeService } from '../charge-code';
import { Project, ProjectService } from '../project';

@Component({
    selector: 'jhi-charge-code-project-dialog',
    templateUrl: './charge-code-project-dialog.component.html'
})
export class ChargeCodeProjectDialogComponent implements OnInit {

    chargeCodeProject: ChargeCodeProject;
    isSaving: boolean;

    chargecodes: ChargeCode[];

    projects: Project[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private chargeCodeProjectService: ChargeCodeProjectService,
        private chargeCodeService: ChargeCodeService,
        private projectService: ProjectService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.chargeCodeService.query()
            .subscribe((res: HttpResponse<ChargeCode[]>) => { this.chargecodes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.projectService.query()
            .subscribe((res: HttpResponse<Project[]>) => { this.projects = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.chargeCodeProject.id !== undefined) {
            this.subscribeToSaveResponse(
                this.chargeCodeProjectService.update(this.chargeCodeProject));
        } else {
            this.subscribeToSaveResponse(
                this.chargeCodeProjectService.create(this.chargeCodeProject));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ChargeCodeProject>>) {
        result.subscribe((res: HttpResponse<ChargeCodeProject>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ChargeCodeProject) {
        this.eventManager.broadcast({ name: 'chargeCodeProjectListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackChargeCodeById(index: number, item: ChargeCode) {
        return item.id;
    }

    trackProjectById(index: number, item: Project) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-charge-code-project-popup',
    template: ''
})
export class ChargeCodeProjectPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private chargeCodeProjectPopupService: ChargeCodeProjectPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.chargeCodeProjectPopupService
                    .open(ChargeCodeProjectDialogComponent as Component, params['id']);
            } else {
                this.chargeCodeProjectPopupService
                    .open(ChargeCodeProjectDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
