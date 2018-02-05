/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../test.module';
import { ChargeCodeProjectDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/charge-code-project/charge-code-project-delete-dialog.component';
import { ChargeCodeProjectService } from '../../../../../../main/webapp/app/entities/charge-code-project/charge-code-project.service';

describe('Component Tests', () => {

    describe('ChargeCodeProject Management Delete Component', () => {
        let comp: ChargeCodeProjectDeleteDialogComponent;
        let fixture: ComponentFixture<ChargeCodeProjectDeleteDialogComponent>;
        let service: ChargeCodeProjectService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [ChargeCodeProjectDeleteDialogComponent],
                providers: [
                    ChargeCodeProjectService
                ]
            })
            .overrideTemplate(ChargeCodeProjectDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChargeCodeProjectDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChargeCodeProjectService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
