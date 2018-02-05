import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ChargeCode } from './charge-code.model';
import { ChargeCodeService } from './charge-code.service';

@Injectable()
export class ChargeCodePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private chargeCodeService: ChargeCodeService

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
                this.chargeCodeService.find(id)
                    .subscribe((chargeCodeResponse: HttpResponse<ChargeCode>) => {
                        const chargeCode: ChargeCode = chargeCodeResponse.body;
                        if (chargeCode.chargeCodeStartDate) {
                            chargeCode.chargeCodeStartDate = {
                                year: chargeCode.chargeCodeStartDate.getFullYear(),
                                month: chargeCode.chargeCodeStartDate.getMonth() + 1,
                                day: chargeCode.chargeCodeStartDate.getDate()
                            };
                        }
                        if (chargeCode.chargeCodeEndDate) {
                            chargeCode.chargeCodeEndDate = {
                                year: chargeCode.chargeCodeEndDate.getFullYear(),
                                month: chargeCode.chargeCodeEndDate.getMonth() + 1,
                                day: chargeCode.chargeCodeEndDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.chargeCodeModalRef(component, chargeCode);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.chargeCodeModalRef(component, new ChargeCode());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    chargeCodeModalRef(component: Component, chargeCode: ChargeCode): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.chargeCode = chargeCode;
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
