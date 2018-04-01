/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemAguaPotableTestModule } from '../../../test.module';
import { ServicioDetailComponent } from '../../../../../../main/webapp/app/entities/servicio/servicio-detail.component';
import { ServicioService } from '../../../../../../main/webapp/app/entities/servicio/servicio.service';
import { Servicio } from '../../../../../../main/webapp/app/entities/servicio/servicio.model';

describe('Component Tests', () => {

    describe('Servicio Management Detail Component', () => {
        let comp: ServicioDetailComponent;
        let fixture: ComponentFixture<ServicioDetailComponent>;
        let service: ServicioService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SystemAguaPotableTestModule],
                declarations: [ServicioDetailComponent],
                providers: [
                    ServicioService
                ]
            })
            .overrideTemplate(ServicioDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ServicioDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServicioService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Servicio(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.servicio).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
