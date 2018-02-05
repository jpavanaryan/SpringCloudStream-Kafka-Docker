/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../test.module';
import { ChargeCodeComponent } from '../../../../../../main/webapp/app/entities/charge-code/charge-code.component';
import { ChargeCodeService } from '../../../../../../main/webapp/app/entities/charge-code/charge-code.service';
import { ChargeCode } from '../../../../../../main/webapp/app/entities/charge-code/charge-code.model';

describe('Component Tests', () => {

    describe('ChargeCode Management Component', () => {
        let comp: ChargeCodeComponent;
        let fixture: ComponentFixture<ChargeCodeComponent>;
        let service: ChargeCodeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [ChargeCodeComponent],
                providers: [
                    ChargeCodeService
                ]
            })
            .overrideTemplate(ChargeCodeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChargeCodeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChargeCodeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ChargeCode(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.chargeCodes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
