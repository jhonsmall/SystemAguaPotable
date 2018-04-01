/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemAguaPotableTestModule } from '../../../test.module';
import { CostoMedidorDetailComponent } from '../../../../../../main/webapp/app/entities/costo-medidor/costo-medidor-detail.component';
import { CostoMedidorService } from '../../../../../../main/webapp/app/entities/costo-medidor/costo-medidor.service';
import { CostoMedidor } from '../../../../../../main/webapp/app/entities/costo-medidor/costo-medidor.model';

describe('Component Tests', () => {

    describe('CostoMedidor Management Detail Component', () => {
        let comp: CostoMedidorDetailComponent;
        let fixture: ComponentFixture<CostoMedidorDetailComponent>;
        let service: CostoMedidorService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SystemAguaPotableTestModule],
                declarations: [CostoMedidorDetailComponent],
                providers: [
                    CostoMedidorService
                ]
            })
            .overrideTemplate(CostoMedidorDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CostoMedidorDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CostoMedidorService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CostoMedidor(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.costoMedidor).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
