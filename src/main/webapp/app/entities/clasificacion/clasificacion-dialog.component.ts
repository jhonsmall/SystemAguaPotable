import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Clasificacion } from './clasificacion.model';
import { ClasificacionPopupService } from './clasificacion-popup.service';
import { ClasificacionService } from './clasificacion.service';

@Component({
    selector: 'jhi-clasificacion-dialog',
    templateUrl: './clasificacion-dialog.component.html'
})
export class ClasificacionDialogComponent implements OnInit {

    clasificacion: Clasificacion;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private clasificacionService: ClasificacionService,
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
        if (this.clasificacion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clasificacionService.update(this.clasificacion));
        } else {
            this.subscribeToSaveResponse(
                this.clasificacionService.create(this.clasificacion));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Clasificacion>>) {
        result.subscribe((res: HttpResponse<Clasificacion>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Clasificacion) {
        this.eventManager.broadcast({ name: 'clasificacionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-clasificacion-popup',
    template: ''
})
export class ClasificacionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clasificacionPopupService: ClasificacionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.clasificacionPopupService
                    .open(ClasificacionDialogComponent as Component, params['id']);
            } else {
                this.clasificacionPopupService
                    .open(ClasificacionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
