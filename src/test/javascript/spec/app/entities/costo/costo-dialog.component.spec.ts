/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SystemAguaPotableTestModule } from '../../../test.module';
import { CostoDialogComponent } from '../../../../../../main/webapp/app/entities/costo/costo-dialog.component';
import { CostoService } from '../../../../../../main/webapp/app/entities/costo/costo.service';
import { Costo } from '../../../../../../main/webapp/app/entities/costo/costo.model';
import { ServicioService } from '../../../../../../main/webapp/app/entities/servicio';
import { SectorService } from '../../../../../../main/webapp/app/entities/sector';
import { ClasificacionService } from '../../../../../../main/webapp/app/entities/clasificacion';

describe('Component Tests', () => {

    describe('Costo Management Dialog Component', () => {
        let comp: CostoDialogComponent;
        let fixture: ComponentFixture<CostoDialogComponent>;
        let service: CostoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SystemAguaPotableTestModule],
                declarations: [CostoDialogComponent],
                providers: [
                    ServicioService,
                    SectorService,
                    ClasificacionService,
                    CostoService
                ]
            })
            .overrideTemplate(CostoDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CostoDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CostoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Costo(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.costo = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'costoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Costo();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.costo = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'costoListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
