/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../test.module';
import { EmployeeProjectComponent } from '../../../../../../main/webapp/app/entities/employee-project/employee-project.component';
import { EmployeeProjectService } from '../../../../../../main/webapp/app/entities/employee-project/employee-project.service';
import { EmployeeProject } from '../../../../../../main/webapp/app/entities/employee-project/employee-project.model';

describe('Component Tests', () => {

    describe('EmployeeProject Management Component', () => {
        let comp: EmployeeProjectComponent;
        let fixture: ComponentFixture<EmployeeProjectComponent>;
        let service: EmployeeProjectService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [EmployeeProjectComponent],
                providers: [
                    EmployeeProjectService
                ]
            })
            .overrideTemplate(EmployeeProjectComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeProjectComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeProjectService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EmployeeProject(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.employeeProjects[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
