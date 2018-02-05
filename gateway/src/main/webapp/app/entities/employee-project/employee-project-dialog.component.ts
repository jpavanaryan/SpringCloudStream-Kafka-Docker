import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EmployeeProject } from './employee-project.model';
import { EmployeeProjectPopupService } from './employee-project-popup.service';
import { EmployeeProjectService } from './employee-project.service';
import { Employee, EmployeeService } from '../employee';
import { Project, ProjectService } from '../project';

@Component({
    selector: 'jhi-employee-project-dialog',
    templateUrl: './employee-project-dialog.component.html'
})
export class EmployeeProjectDialogComponent implements OnInit {

    employeeProject: EmployeeProject;
    isSaving: boolean;

    employees: Employee[];

    projects: Project[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private employeeProjectService: EmployeeProjectService,
        private employeeService: EmployeeService,
        private projectService: ProjectService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.employeeService.query()
            .subscribe((res: HttpResponse<Employee[]>) => { this.employees = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.projectService.query()
            .subscribe((res: HttpResponse<Project[]>) => { this.projects = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.employeeProject.id !== undefined) {
            this.subscribeToSaveResponse(
                this.employeeProjectService.update(this.employeeProject));
        } else {
            this.subscribeToSaveResponse(
                this.employeeProjectService.create(this.employeeProject));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EmployeeProject>>) {
        result.subscribe((res: HttpResponse<EmployeeProject>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EmployeeProject) {
        this.eventManager.broadcast({ name: 'employeeProjectListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackEmployeeById(index: number, item: Employee) {
        return item.id;
    }

    trackProjectById(index: number, item: Project) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-employee-project-popup',
    template: ''
})
export class EmployeeProjectPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeProjectPopupService: EmployeeProjectPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.employeeProjectPopupService
                    .open(EmployeeProjectDialogComponent as Component, params['id']);
            } else {
                this.employeeProjectPopupService
                    .open(EmployeeProjectDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
