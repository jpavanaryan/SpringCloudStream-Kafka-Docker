/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../test.module';
import { ChargeCodeProjectComponent } from '../../../../../../main/webapp/app/entities/charge-code-project/charge-code-project.component';
import { ChargeCodeProjectService } from '../../../../../../main/webapp/app/entities/charge-code-project/charge-code-project.service';
import { ChargeCodeProject } from '../../../../../../main/webapp/app/entities/charge-code-project/charge-code-project.model';

describe('Component Tests', () => {

    describe('ChargeCodeProject Management Component', () => {
        let comp: ChargeCodeProjectComponent;
        let fixture: ComponentFixture<ChargeCodeProjectComponent>;
        let service: ChargeCodeProjectService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [ChargeCodeProjectComponent],
                providers: [
                    ChargeCodeProjectService
                ]
            })
            .overrideTemplate(ChargeCodeProjectComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChargeCodeProjectComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChargeCodeProjectService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ChargeCodeProject(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.chargeCodeProjects[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
