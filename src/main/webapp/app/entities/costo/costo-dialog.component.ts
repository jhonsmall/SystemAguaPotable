import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Costo } from './costo.model';
import { CostoPopupService } from './costo-popup.service';
import { CostoService } from './costo.service';
import { Servicio, ServicioService } from '../servicio';
import { Sector, SectorService } from '../sector';
import { Clasificacion, ClasificacionService } from '../clasificacion';

@Component({
    selector: 'jhi-costo-dialog',
    templateUrl: './costo-dialog.component.html'
})
export class CostoDialogComponent implements OnInit {

    costo: Costo;
    isSaving: boolean;

    servicios: Servicio[];

    sectors: Sector[];

    clasificacions: Clasificacion[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private costoService: CostoService,
        private servicioService: ServicioService,
        private sectorService: SectorService,
        private clasificacionService: ClasificacionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.servicioService.query()
            .subscribe((res: HttpResponse<Servicio[]>) => { this.servicios = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.sectorService.query()
            .subscribe((res: HttpResponse<Sector[]>) => { this.sectors = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.clasificacionService.query()
            .subscribe((res: HttpResponse<Clasificacion[]>) => { this.clasificacions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.costo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.costoService.update(this.costo));
        } else {
            this.subscribeToSaveResponse(
                this.costoService.create(this.costo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Costo>>) {
        result.subscribe((res: HttpResponse<Costo>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Costo) {
        this.eventManager.broadcast({ name: 'costoListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackServicioById(index: number, item: Servicio) {
        return item.id;
    }

    trackSectorById(index: number, item: Sector) {
        return item.id;
    }

    trackClasificacionById(index: number, item: Clasificacion) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-costo-popup',
    template: ''
})
export class CostoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private costoPopupService: CostoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.costoPopupService
                    .open(CostoDialogComponent as Component, params['id']);
            } else {
                this.costoPopupService
                    .open(CostoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
