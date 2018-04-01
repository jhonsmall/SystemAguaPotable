/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemAguaPotableTestModule } from '../../../test.module';
import { ClasificacionDetailComponent } from '../../../../../../main/webapp/app/entities/clasificacion/clasificacion-detail.component';
import { ClasificacionService } from '../../../../../../main/webapp/app/entities/clasificacion/clasificacion.service';
import { Clasificacion } from '../../../../../../main/webapp/app/entities/clasificacion/clasificacion.model';

describe('Component Tests', () => {

    describe('Clasificacion Management Detail Component', () => {
        let comp: ClasificacionDetailComponent;
        let fixture: ComponentFixture<ClasificacionDetailComponent>;
        let service: ClasificacionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SystemAguaPotableTestModule],
                declarations: [ClasificacionDetailComponent],
                providers: [
                    ClasificacionService
                ]
            })
            .overrideTemplate(ClasificacionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClasificacionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClasificacionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Clasificacion(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.clasificacion).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
