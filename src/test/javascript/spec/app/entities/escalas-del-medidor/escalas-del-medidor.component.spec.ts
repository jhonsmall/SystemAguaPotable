/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SystemAguaPotableTestModule } from '../../../test.module';
import { EscalasDelMedidorComponent } from '../../../../../../main/webapp/app/entities/escalas-del-medidor/escalas-del-medidor.component';
import { EscalasDelMedidorService } from '../../../../../../main/webapp/app/entities/escalas-del-medidor/escalas-del-medidor.service';
import { EscalasDelMedidor } from '../../../../../../main/webapp/app/entities/escalas-del-medidor/escalas-del-medidor.model';

describe('Component Tests', () => {

    describe('EscalasDelMedidor Management Component', () => {
        let comp: EscalasDelMedidorComponent;
        let fixture: ComponentFixture<EscalasDelMedidorComponent>;
        let service: EscalasDelMedidorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SystemAguaPotableTestModule],
                declarations: [EscalasDelMedidorComponent],
                providers: [
                    EscalasDelMedidorService
                ]
            })
            .overrideTemplate(EscalasDelMedidorComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EscalasDelMedidorComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EscalasDelMedidorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EscalasDelMedidor(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.escalasDelMedidors[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
