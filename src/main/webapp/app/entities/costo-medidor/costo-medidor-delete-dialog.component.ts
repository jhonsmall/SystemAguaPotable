import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CostoMedidor } from './costo-medidor.model';
import { CostoMedidorPopupService } from './costo-medidor-popup.service';
import { CostoMedidorService } from './costo-medidor.service';

@Component({
    selector: 'jhi-costo-medidor-delete-dialog',
    templateUrl: './costo-medidor-delete-dialog.component.html'
})
export class CostoMedidorDeleteDialogComponent {

    costoMedidor: CostoMedidor;

    constructor(
        private costoMedidorService: CostoMedidorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.costoMedidorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'costoMedidorListModification',
                content: 'Deleted an costoMedidor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-costo-medidor-delete-popup',
    template: ''
})
export class CostoMedidorDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private costoMedidorPopupService: CostoMedidorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.costoMedidorPopupService
                .open(CostoMedidorDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
