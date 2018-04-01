import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Clasificacion } from './clasificacion.model';
import { ClasificacionPopupService } from './clasificacion-popup.service';
import { ClasificacionService } from './clasificacion.service';

@Component({
    selector: 'jhi-clasificacion-delete-dialog',
    templateUrl: './clasificacion-delete-dialog.component.html'
})
export class ClasificacionDeleteDialogComponent {

    clasificacion: Clasificacion;

    constructor(
        private clasificacionService: ClasificacionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clasificacionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'clasificacionListModification',
                content: 'Deleted an clasificacion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-clasificacion-delete-popup',
    template: ''
})
export class ClasificacionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clasificacionPopupService: ClasificacionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.clasificacionPopupService
                .open(ClasificacionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
