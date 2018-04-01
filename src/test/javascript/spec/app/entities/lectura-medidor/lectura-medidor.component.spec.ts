/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SystemAguaPotableTestModule } from '../../../test.module';
import { LecturaMedidorComponent } from '../../../../../../main/webapp/app/entities/lectura-medidor/lectura-medidor.component';
import { LecturaMedidorService } from '../../../../../../main/webapp/app/entities/lectura-medidor/lectura-medidor.service';
import { LecturaMedidor } from '../../../../../../main/webapp/app/entities/lectura-medidor/lectura-medidor.model';

describe('Component Tests', () => {

    describe('LecturaMedidor Management Component', () => {
        let comp: LecturaMedidorComponent;
        let fixture: ComponentFixture<LecturaMedidorComponent>;
        let service: LecturaMedidorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SystemAguaPotableTestModule],
                declarations: [LecturaMedidorComponent],
                providers: [
                    LecturaMedidorService
                ]
            })
            .overrideTemplate(LecturaMedidorComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LecturaMedidorComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LecturaMedidorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new LecturaMedidor(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.lecturaMedidors[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
