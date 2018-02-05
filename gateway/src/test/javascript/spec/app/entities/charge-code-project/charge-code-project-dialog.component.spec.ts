/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { GatewayTestModule } from '../../../test.module';
import { ChargeCodeProjectDialogComponent } from '../../../../../../main/webapp/app/entities/charge-code-project/charge-code-project-dialog.component';
import { ChargeCodeProjectService } from '../../../../../../main/webapp/app/entities/charge-code-project/charge-code-project.service';
import { ChargeCodeProject } from '../../../../../../main/webapp/app/entities/charge-code-project/charge-code-project.model';
import { ChargeCodeService } from '../../../../../../main/webapp/app/entities/charge-code';
import { ProjectService } from '../../../../../../main/webapp/app/entities/project';

describe('Component Tests', () => {

    describe('ChargeCodeProject Management Dialog Component', () => {
        let comp: ChargeCodeProjectDialogComponent;
        let fixture: ComponentFixture<ChargeCodeProjectDialogComponent>;
        let service: ChargeCodeProjectService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [ChargeCodeProjectDialogComponent],
                providers: [
                    ChargeCodeService,
                    ProjectService,
                    ChargeCodeProjectService
                ]
            })
            .overrideTemplate(ChargeCodeProjectDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChargeCodeProjectDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChargeCodeProjectService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ChargeCodeProject(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.chargeCodeProject = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'chargeCodeProjectListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ChargeCodeProject();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.chargeCodeProject = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'chargeCodeProjectListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
