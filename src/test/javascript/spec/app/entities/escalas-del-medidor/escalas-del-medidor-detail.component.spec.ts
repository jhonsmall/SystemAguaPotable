/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemAguaPotableTestModule } from '../../../test.module';
import { EscalasDelMedidorDetailComponent } from '../../../../../../main/webapp/app/entities/escalas-del-medidor/escalas-del-medidor-detail.component';
import { EscalasDelMedidorService } from '../../../../../../main/webapp/app/entities/escalas-del-medidor/escalas-del-medidor.service';
import { EscalasDelMedidor } from '../../../../../../main/webapp/app/entities/escalas-del-medidor/escalas-del-medidor.model';

describe('Component Tests', () => {

    describe('EscalasDelMedidor Management Detail Component', () => {
        let comp: EscalasDelMedidorDetailComponent;
        let fixture: ComponentFixture<EscalasDelMedidorDetailComponent>;
        let service: EscalasDelMedidorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SystemAguaPotableTestModule],
                declarations: [EscalasDelMedidorDetailComponent],
                providers: [
                    EscalasDelMedidorService
                ]
            })
            .overrideTemplate(EscalasDelMedidorDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EscalasDelMedidorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EscalasDelMedidorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EscalasDelMedidor(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.escalasDelMedidor).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
