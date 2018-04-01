/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SystemAguaPotableTestModule } from '../../../test.module';
import { CostoMedidorDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/costo-medidor/costo-medidor-delete-dialog.component';
import { CostoMedidorService } from '../../../../../../main/webapp/app/entities/costo-medidor/costo-medidor.service';

describe('Component Tests', () => {

    describe('CostoMedidor Management Delete Component', () => {
        let comp: CostoMedidorDeleteDialogComponent;
        let fixture: ComponentFixture<CostoMedidorDeleteDialogComponent>;
        let service: CostoMedidorService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SystemAguaPotableTestModule],
                declarations: [CostoMedidorDeleteDialogComponent],
                providers: [
                    CostoMedidorService
                ]
            })
            .overrideTemplate(CostoMedidorDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CostoMedidorDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CostoMedidorService);
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
