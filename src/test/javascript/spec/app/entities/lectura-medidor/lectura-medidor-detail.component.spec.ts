/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemAguaPotableTestModule } from '../../../test.module';
import { LecturaMedidorDetailComponent } from '../../../../../../main/webapp/app/entities/lectura-medidor/lectura-medidor-detail.component';
import { LecturaMedidorService } from '../../../../../../main/webapp/app/entities/lectura-medidor/lectura-medidor.service';
import { LecturaMedidor } from '../../../../../../main/webapp/app/entities/lectura-medidor/lectura-medidor.model';

describe('Component Tests', () => {

    describe('LecturaMedidor Management Detail Component', () => {
        let comp: LecturaMedidorDetailComponent;
        let fixture: ComponentFixture<LecturaMedidorDetailComponent>;
        let service: LecturaMedidorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SystemAguaPotableTestModule],
                declarations: [LecturaMedidorDetailComponent],
                providers: [
                    LecturaMedidorService
                ]
            })
            .overrideTemplate(LecturaMedidorDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LecturaMedidorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LecturaMedidorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new LecturaMedidor(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.lecturaMedidor).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
