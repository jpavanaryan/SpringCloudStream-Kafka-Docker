import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ChargeCode } from './charge-code.model';
import { ChargeCodeService } from './charge-code.service';

@Component({
    selector: 'jhi-charge-code-detail',
    templateUrl: './charge-code-detail.component.html'
})
export class ChargeCodeDetailComponent implements OnInit, OnDestroy {

    chargeCode: ChargeCode;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private chargeCodeService: ChargeCodeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInChargeCodes();
    }

    load(id) {
        this.chargeCodeService.find(id)
            .subscribe((chargeCodeResponse: HttpResponse<ChargeCode>) => {
                this.chargeCode = chargeCodeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInChargeCodes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'chargeCodeListModification',
            (response) => this.load(this.chargeCode.id)
        );
    }
}
