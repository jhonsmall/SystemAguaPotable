/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SystemAguaPotableTestModule } from '../../../test.module';
import { EscalasDelMedidorDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/escalas-del-medidor/escalas-del-medidor-delete-dialog.component';
import { EscalasDelMedidorService } from '../../../../../../main/webapp/app/entities/escalas-del-medidor/escalas-del-medidor.service';

describe('Component Tests', () => {

    describe('EscalasDelMedidor Management Delete Component', () => {
        let comp: EscalasDelMedidorDeleteDialogComponent;
        let fixture: ComponentFixture<EscalasDelMedidorDeleteDialogComponent>;
        let service: EscalasDelMedidorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SystemAguaPotableTestModule],
                declarations: [EscalasDelMedidorDeleteDialogComponent],
                providers: [
                    EscalasDelMedidorService
                ]
            })
            .overrideTemplate(EscalasDelMedidorDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EscalasDelMedidorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EscalasDelMedidorService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
