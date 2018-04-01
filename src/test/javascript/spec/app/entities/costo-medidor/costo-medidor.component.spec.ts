/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SystemAguaPotableTestModule } from '../../../test.module';
import { CostoMedidorComponent } from '../../../../../../main/webapp/app/entities/costo-medidor/costo-medidor.component';
import { CostoMedidorService } from '../../../../../../main/webapp/app/entities/costo-medidor/costo-medidor.service';
import { CostoMedidor } from '../../../../../../main/webapp/app/entities/costo-medidor/costo-medidor.model';

describe('Component Tests', () => {

    describe('CostoMedidor Management Component', () => {
        let comp: CostoMedidorComponent;
        let fixture: ComponentFixture<CostoMedidorComponent>;
        let service: CostoMedidorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SystemAguaPotableTestModule],
                declarations: [CostoMedidorComponent],
                providers: [
                    CostoMedidorService
                ]
            })
            .overrideTemplate(CostoMedidorComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CostoMedidorComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CostoMedidorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CostoMedidor(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.costoMedidors[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
