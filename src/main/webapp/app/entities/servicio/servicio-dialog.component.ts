import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Servicio } from './servicio.model';
import { ServicioPopupService } from './servicio-popup.service';
import { ServicioService } from './servicio.service';

@Component({
    selector: 'jhi-servicio-dialog',
    templateUrl: './servicio-dialog.component.html'
})
export class ServicioDialogComponent implements OnInit {

    servicio: Servicio;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private servicioService: ServicioService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.servicio.id !== undefined) {
            this.subscribeToSaveResponse(
                this.servicioService.update(this.servicio));
        } else {
            this.subscribeToSaveResponse(
                this.servicioService.create(this.servicio));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Servicio>>) {
        result.subscribe((res: HttpResponse<Servicio>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Servicio) {
        this.eventManager.broadcast({ name: 'servicioListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-servicio-popup',
    template: ''
})
export class ServicioPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private servicioPopupService: ServicioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.servicioPopupService
                    .open(ServicioDialogComponent as Component, params['id']);
            } else {
                this.servicioPopupService
                    .open(ServicioDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
