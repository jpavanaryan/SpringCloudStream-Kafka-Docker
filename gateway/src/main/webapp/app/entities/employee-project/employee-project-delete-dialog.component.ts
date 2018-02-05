import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeProject } from './employee-project.model';
import { EmployeeProjectPopupService } from './employee-project-popup.service';
import { EmployeeProjectService } from './employee-project.service';

@Component({
    selector: 'jhi-employee-project-delete-dialog',
    templateUrl: './employee-project-delete-dialog.component.html'
})
export class EmployeeProjectDeleteDialogComponent {

    employeeProject: EmployeeProject;

    constructor(
        private employeeProjectService: EmployeeProjectService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.employeeProjectService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'employeeProjectListModification',
                content: 'Deleted an employeeProject'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-employee-project-delete-popup',
    template: ''
})
export class EmployeeProjectDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private employeeProjectPopupService: EmployeeProjectPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.employeeProjectPopupService
                .open(EmployeeProjectDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
