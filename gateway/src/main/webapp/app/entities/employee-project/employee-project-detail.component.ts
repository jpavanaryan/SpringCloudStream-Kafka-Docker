import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EmployeeProject } from './employee-project.model';
import { EmployeeProjectService } from './employee-project.service';

@Component({
    selector: 'jhi-employee-project-detail',
    templateUrl: './employee-project-detail.component.html'
})
export class EmployeeProjectDetailComponent implements OnInit, OnDestroy {

    employeeProject: EmployeeProject;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private employeeProjectService: EmployeeProjectService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEmployeeProjects();
    }

    load(id) {
        this.employeeProjectService.find(id)
            .subscribe((employeeProjectResponse: HttpResponse<EmployeeProject>) => {
                this.employeeProject = employeeProjectResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEmployeeProjects() {
        this.eventSubscriber = this.eventManager.subscribe(
            'employeeProjectListModification',
            (response) => this.load(this.employeeProject.id)
        );
    }
}
