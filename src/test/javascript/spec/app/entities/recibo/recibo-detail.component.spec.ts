/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SystemAguaPotableTestModule } from '../../../test.module';
import { ReciboDetailComponent } from '../../../../../../main/webapp/app/entities/recibo/recibo-detail.component';
import { ReciboService } from '../../../../../../main/webapp/app/entities/recibo/recibo.service';
import { Recibo } from '../../../../../../main/webapp/app/entities/recibo/recibo.model';

describe('Component Tests', () => {

    describe('Recibo Management Detail Component', () => {
        let comp: ReciboDetailComponent;
        let fixture: ComponentFixture<ReciboDetailComponent>;
        let service: ReciboService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SystemAguaPotableTestModule],
                declarations: [ReciboDetailComponent],
                providers: [
                    ReciboService
                ]
            })
            .overrideTemplate(ReciboDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReciboDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReciboService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Recibo(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.recibo).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
