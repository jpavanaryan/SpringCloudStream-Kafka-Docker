import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ChargeCodeProject } from './charge-code-project.model';
import { ChargeCodeProjectService } from './charge-code-project.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-charge-code-project',
    templateUrl: './charge-code-project.component.html'
})
export class ChargeCodeProjectComponent implements OnInit, OnDestroy {
chargeCodeProjects: ChargeCodeProject[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private chargeCodeProjectService: ChargeCodeProjectService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.chargeCodeProjectService.query().subscribe(
            (res: HttpResponse<ChargeCodeProject[]>) => {
                this.chargeCodeProjects = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInChargeCodeProjects();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ChargeCodeProject) {
        return item.id;
    }
    registerChangeInChargeCodeProjects() {
        this.eventSubscriber = this.eventManager.subscribe('chargeCodeProjectListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
