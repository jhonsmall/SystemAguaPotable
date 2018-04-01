/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SystemAguaPotableTestModule } from '../../../test.module';
import { ClasificacionComponent } from '../../../../../../main/webapp/app/entities/clasificacion/clasificacion.component';
import { ClasificacionService } from '../../../../../../main/webapp/app/entities/clasificacion/clasificacion.service';
import { Clasificacion } from '../../../../../../main/webapp/app/entities/clasificacion/clasificacion.model';

describe('Component Tests', () => {

    describe('Clasificacion Management Component', () => {
        let comp: ClasificacionComponent;
        let fixture: ComponentFixture<ClasificacionComponent>;
        let service: ClasificacionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SystemAguaPotableTestModule],
                declarations: [ClasificacionComponent],
                providers: [
                    ClasificacionService
                ]
            })
            .overrideTemplate(ClasificacionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClasificacionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClasificacionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Clasificacion(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.clasificacions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
