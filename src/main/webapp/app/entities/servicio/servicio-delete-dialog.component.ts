import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Servicio } from './servicio.model';
import { ServicioPopupService } from './servicio-popup.service';
import { ServicioService } from './servicio.service';

@Component({
    selector: 'jhi-servicio-delete-dialog',
    templateUrl: './servicio-delete-dialog.component.html'
})
export class ServicioDeleteDialogComponent {

    servicio: Servicio;

    constructor(
        private servicioService: ServicioService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.servicioService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'servicioListModification',
                content: 'Deleted an servicio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-servicio-delete-popup',
    template: ''
})
export class ServicioDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private servicioPopupService: ServicioPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.servicioPopupService
                .open(ServicioDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
