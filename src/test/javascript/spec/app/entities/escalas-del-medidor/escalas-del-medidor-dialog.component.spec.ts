/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SystemAguaPotableTestModule } from '../../../test.module';
import { EscalasDelMedidorDialogComponent } from '../../../../../../main/webapp/app/entities/escalas-del-medidor/escalas-del-medidor-dialog.component';
import { EscalasDelMedidorService } from '../../../../../../main/webapp/app/entities/escalas-del-medidor/escalas-del-medidor.service';
import { EscalasDelMedidor } from '../../../../../../main/webapp/app/entities/escalas-del-medidor/escalas-del-medidor.model';
import { ClasificacionService } from '../../../../../../main/webapp/app/entities/clasificacion';

describe('Component Tests', () => {

    describe('EscalasDelMedidor Management Dialog Component', () => {
        let comp: EscalasDelMedidorDialogComponent;
        let fixture: ComponentFixture<EscalasDelMedidorDialogComponent>;
        let service: EscalasDelMedidorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SystemAguaPotableTestModule],
                declarations: [EscalasDelMedidorDialogComponent],
                providers: [
                    ClasificacionService,
                    EscalasDelMedidorService
                ]
            })
            .overrideTemplate(EscalasDelMedidorDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EscalasDelMedidorDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EscalasDelMedidorService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EscalasDelMedidor(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.escalasDelMedidor = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'escalasDelMedidorListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EscalasDelMedidor();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.escalasDelMedidor = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'escalasDelMedidorListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
