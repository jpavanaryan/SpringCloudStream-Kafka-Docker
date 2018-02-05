/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GatewayTestModule } from '../../../test.module';
import { ChargeCodeProjectDetailComponent } from '../../../../../../main/webapp/app/entities/charge-code-project/charge-code-project-detail.component';
import { ChargeCodeProjectService } from '../../../../../../main/webapp/app/entities/charge-code-project/charge-code-project.service';
import { ChargeCodeProject } from '../../../../../../main/webapp/app/entities/charge-code-project/charge-code-project.model';

describe('Component Tests', () => {

    describe('ChargeCodeProject Management Detail Component', () => {
        let comp: ChargeCodeProjectDetailComponent;
        let fixture: ComponentFixture<ChargeCodeProjectDetailComponent>;
        let service: ChargeCodeProjectService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [ChargeCodeProjectDetailComponent],
                providers: [
                    ChargeCodeProjectService
                ]
            })
            .overrideTemplate(ChargeCodeProjectDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChargeCodeProjectDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChargeCodeProjectService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ChargeCodeProject(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.chargeCodeProject).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
