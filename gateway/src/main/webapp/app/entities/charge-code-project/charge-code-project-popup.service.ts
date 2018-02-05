import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ChargeCodeProject } from './charge-code-project.model';
import { ChargeCodeProjectService } from './charge-code-project.service';

@Injectable()
export class ChargeCodeProjectPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private chargeCodeProjectService: ChargeCodeProjectService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.chargeCodeProjectService.find(id)
                    .subscribe((chargeCodeProjectResponse: HttpResponse<ChargeCodeProject>) => {
                        const chargeCodeProject: ChargeCodeProject = chargeCodeProjectResponse.body;
                        this.ngbModalRef = this.chargeCodeProjectModalRef(component, chargeCodeProject);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.chargeCodeProjectModalRef(component, new ChargeCodeProject());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    chargeCodeProjectModalRef(component: Component, chargeCodeProject: ChargeCodeProject): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.chargeCodeProject = chargeCodeProject;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
