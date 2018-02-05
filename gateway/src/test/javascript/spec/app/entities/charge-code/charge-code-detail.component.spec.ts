/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GatewayTestModule } from '../../../test.module';
import { ChargeCodeDetailComponent } from '../../../../../../main/webapp/app/entities/charge-code/charge-code-detail.component';
import { ChargeCodeService } from '../../../../../../main/webapp/app/entities/charge-code/charge-code.service';
import { ChargeCode } from '../../../../../../main/webapp/app/entities/charge-code/charge-code.model';

describe('Component Tests', () => {

    describe('ChargeCode Management Detail Component', () => {
        let comp: ChargeCodeDetailComponent;
        let fixture: ComponentFixture<ChargeCodeDetailComponent>;
        let service: ChargeCodeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [ChargeCodeDetailComponent],
                providers: [
                    ChargeCodeService
                ]
            })
            .overrideTemplate(ChargeCodeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChargeCodeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChargeCodeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ChargeCode(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.chargeCode).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
