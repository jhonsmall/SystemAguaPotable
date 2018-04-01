import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EscalasDelMedidor } from './escalas-del-medidor.model';
import { EscalasDelMedidorPopupService } from './escalas-del-medidor-popup.service';
import { EscalasDelMedidorService } from './escalas-del-medidor.service';
import { Clasificacion, ClasificacionService } from '../clasificacion';

@Component({
    selector: 'jhi-escalas-del-medidor-dialog',
    templateUrl: './escalas-del-medidor-dialog.component.html'
})
export class EscalasDelMedidorDialogComponent implements OnInit {

    escalasDelMedidor: EscalasDelMedidor;
    isSaving: boolean;

    clasificacions: Clasificacion[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private escalasDelMedidorService: EscalasDelMedidorService,
        private clasificacionService: ClasificacionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.clasificacionService.query()
            .subscribe((res: HttpResponse<Clasificacion[]>) => { this.clasificacions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.escalasDelMedidor.id !== undefined) {
            this.subscribeToSaveResponse(
                this.escalasDelMedidorService.update(this.escalasDelMedidor));
        } else {
            this.subscribeToSaveResponse(
                this.escalasDelMedidorService.create(this.escalasDelMedidor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EscalasDelMedidor>>) {
        result.subscribe((res: HttpResponse<EscalasDelMedidor>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EscalasDelMedidor) {
        this.eventManager.broadcast({ name: 'escalasDelMedidorListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackClasificacionById(index: number, item: Clasificacion) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-escalas-del-medidor-popup',
    template: ''
})
export class EscalasDelMedidorPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private escalasDelMedidorPopupService: EscalasDelMedidorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.escalasDelMedidorPopupService
                    .open(EscalasDelMedidorDialogComponent as Component, params['id']);
            } else {
                this.escalasDelMedidorPopupService
                    .open(EscalasDelMedidorDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
