/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GatewayTestModule } from '../../../test.module';
import { EmployeeProjectDetailComponent } from '../../../../../../main/webapp/app/entities/employee-project/employee-project-detail.component';
import { EmployeeProjectService } from '../../../../../../main/webapp/app/entities/employee-project/employee-project.service';
import { EmployeeProject } from '../../../../../../main/webapp/app/entities/employee-project/employee-project.model';

describe('Component Tests', () => {

    describe('EmployeeProject Management Detail Component', () => {
        let comp: EmployeeProjectDetailComponent;
        let fixture: ComponentFixture<EmployeeProjectDetailComponent>;
        let service: EmployeeProjectService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [EmployeeProjectDetailComponent],
                providers: [
                    EmployeeProjectService
                ]
            })
            .overrideTemplate(EmployeeProjectDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EmployeeProjectDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EmployeeProjectService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EmployeeProject(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.employeeProject).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
