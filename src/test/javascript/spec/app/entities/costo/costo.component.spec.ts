/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SystemAguaPotableTestModule } from '../../../test.module';
import { CostoComponent } from '../../../../../../main/webapp/app/entities/costo/costo.component';
import { CostoService } from '../../../../../../main/webapp/app/entities/costo/costo.service';
import { Costo } from '../../../../../../main/webapp/app/entities/costo/costo.model';

describe('Component Tests', () => {

    describe('Costo Management Component', () => {
        let comp: CostoComponent;
        let fixture: ComponentFixture<CostoComponent>;
        let service: CostoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SystemAguaPotableTestModule],
                declarations: [CostoComponent],
                providers: [
                    CostoService
                ]
            })
            .overrideTemplate(CostoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CostoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CostoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Costo(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.costos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
