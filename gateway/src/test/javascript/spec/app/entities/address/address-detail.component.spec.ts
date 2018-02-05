/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { GatewayTestModule } from '../../../test.module';
import { AddressDetailComponent } from '../../../../../../main/webapp/app/entities/address/address-detail.component';
import { AddressService } from '../../../../../../main/webapp/app/entities/address/address.service';
import { Address } from '../../../../../../main/webapp/app/entities/address/address.model';

describe('Component Tests', () => {

    describe('Address Management Detail Component', () => {
        let comp: AddressDetailComponent;
        let fixture: ComponentFixture<AddressDetailComponent>;
        let service: AddressService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [AddressDetailComponent],
                providers: [
                    AddressService
                ]
            })
            .overrideTemplate(AddressDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AddressDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AddressService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Address(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.address).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
