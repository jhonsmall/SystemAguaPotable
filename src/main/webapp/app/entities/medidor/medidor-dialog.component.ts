import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Medidor } from './medidor.model';
import { MedidorPopupService } from './medidor-popup.service';
import { MedidorService } from './medidor.service';
import { Usuario, UsuarioService } from '../usuario';
import { Sector, SectorService } from '../sector';
import { Clasificacion, ClasificacionService } from '../clasificacion';

@Component({
    selector: 'jhi-medidor-dialog',
    templateUrl: './medidor-dialog.component.html'
})
export class MedidorDialogComponent implements OnInit {

    medidor: Medidor;
    isSaving: boolean;

    usuarios: Usuario[];

    sectors: Sector[];

    clasificacions: Clasificacion[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private medidorService: MedidorService,
        private usuarioService: UsuarioService,
        private sectorService: SectorService,
        private clasificacionService: ClasificacionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.usuarioService.query()
            .subscribe((res: HttpResponse<Usuario[]>) => { this.usuarios = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.medidor.id !== undefined) {
            this.subscribeToSaveResponse(
                this.medidorService.update(this.medidor));
        } else {
            this.subscribeToSaveResponse(
                this.medidorService.create(this.medidor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Medidor>>) {
        result.subscribe((res: HttpResponse<Medidor>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Medidor) {
        this.eventManager.broadcast({ name: 'medidorListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUsuarioById(index: number, item: Usuario) {
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
    selector: 'jhi-medidor-popup',
    template: ''
})
export class MedidorPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private medidorPopupService: MedidorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.medidorPopupService
                    .open(MedidorDialogComponent as Component, params['id']);
            } else {
                this.medidorPopupService
                    .open(MedidorDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
