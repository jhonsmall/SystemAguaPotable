/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SystemAguaPotableTestModule } from '../../../test.module';
import { CostoMedidorDialogComponent } from '../../../../../../main/webapp/app/entities/costo-medidor/costo-medidor-dialog.component';
import { CostoMedidorService } from '../../../../../../main/webapp/app/entities/costo-medidor/costo-medidor.service';
import { CostoMedidor } from '../../../../../../main/webapp/app/entities/costo-medidor/costo-medidor.model';
import { CostoService } from '../../../../../../main/webapp/app/entities/costo';
import { MedidorService } from '../../../../../../main/webapp/app/entities/medidor';

describe('Component Tests', () => {

    describe('CostoMedidor Management Dialog Component', () => {
        let comp: CostoMedidorDialogComponent;
        let fixture: ComponentFixture<CostoMedidorDialogComponent>;
        let service: CostoMedidorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SystemAguaPotableTestModule],
                declarations: [CostoMedidorDialogComponent],
                providers: [
                    CostoService,
                    MedidorService,
                    CostoMedidorService
                ]
            })
            .overrideTemplate(CostoMedidorDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CostoMedidorDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CostoMedidorService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CostoMedidor(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.costoMedidor = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'costoMedidorListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CostoMedidor();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.costoMedidor = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'costoMedidorListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
