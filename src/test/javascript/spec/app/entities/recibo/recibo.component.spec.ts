/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SystemAguaPotableTestModule } from '../../../test.module';
import { ReciboComponent } from '../../../../../../main/webapp/app/entities/recibo/recibo.component';
import { ReciboService } from '../../../../../../main/webapp/app/entities/recibo/recibo.service';
import { Recibo } from '../../../../../../main/webapp/app/entities/recibo/recibo.model';

describe('Component Tests', () => {

    describe('Recibo Management Component', () => {
        let comp: ReciboComponent;
        let fixture: ComponentFixture<ReciboComponent>;
        let service: ReciboService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SystemAguaPotableTestModule],
                declarations: [ReciboComponent],
                providers: [
                    ReciboService
                ]
            })
            .overrideTemplate(ReciboComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReciboComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReciboService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Recibo(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.recibos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
