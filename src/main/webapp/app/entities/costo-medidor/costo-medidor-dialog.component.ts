import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CostoMedidor } from './costo-medidor.model';
import { CostoMedidorPopupService } from './costo-medidor-popup.service';
import { CostoMedidorService } from './costo-medidor.service';
import { Costo, CostoService } from '../costo';
import { Medidor, MedidorService } from '../medidor';

@Component({
    selector: 'jhi-costo-medidor-dialog',
    templateUrl: './costo-medidor-dialog.component.html'
})
export class CostoMedidorDialogComponent implements OnInit {

    costoMedidor: CostoMedidor;
    isSaving: boolean;

    costos: Costo[];

    medidors: Medidor[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private costoMedidorService: CostoMedidorService,
        private costoService: CostoService,
        private medidorService: MedidorService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.costoService.query()
            .subscribe((res: HttpResponse<Costo[]>) => { this.costos = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.medidorService.query()
            .subscribe((res: HttpResponse<Medidor[]>) => { this.medidors = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.costoMedidor.id !== undefined) {
            this.subscribeToSaveResponse(
                this.costoMedidorService.update(this.costoMedidor));
        } else {
            this.subscribeToSaveResponse(
                this.costoMedidorService.create(this.costoMedidor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CostoMedidor>>) {
        result.subscribe((res: HttpResponse<CostoMedidor>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CostoMedidor) {
        this.eventManager.broadcast({ name: 'costoMedidorListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCostoById(index: number, item: Costo) {
        return item.id;
    }

    trackMedidorById(index: number, item: Medidor) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-costo-medidor-popup',
    template: ''
})
export class CostoMedidorPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private costoMedidorPopupService: CostoMedidorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.costoMedidorPopupService
                    .open(CostoMedidorDialogComponent as Component, params['id']);
            } else {
                this.costoMedidorPopupService
                    .open(CostoMedidorDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
