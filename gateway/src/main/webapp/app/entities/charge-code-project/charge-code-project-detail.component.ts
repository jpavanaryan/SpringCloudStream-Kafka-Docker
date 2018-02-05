import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ChargeCodeProject } from './charge-code-project.model';
import { ChargeCodeProjectService } from './charge-code-project.service';

@Component({
    selector: 'jhi-charge-code-project-detail',
    templateUrl: './charge-code-project-detail.component.html'
})
export class ChargeCodeProjectDetailComponent implements OnInit, OnDestroy {

    chargeCodeProject: ChargeCodeProject;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private chargeCodeProjectService: ChargeCodeProjectService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInChargeCodeProjects();
    }

    load(id) {
        this.chargeCodeProjectService.find(id)
            .subscribe((chargeCodeProjectResponse: HttpResponse<ChargeCodeProject>) => {
                this.chargeCodeProject = chargeCodeProjectResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInChargeCodeProjects() {
        this.eventSubscriber = this.eventManager.subscribe(
            'chargeCodeProjectListModification',
            (response) => this.load(this.chargeCodeProject.id)
        );
    }
}
