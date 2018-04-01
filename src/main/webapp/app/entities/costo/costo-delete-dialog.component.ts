import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Costo } from './costo.model';
import { CostoPopupService } from './costo-popup.service';
import { CostoService } from './costo.service';

@Component({
    selector: 'jhi-costo-delete-dialog',
    templateUrl: './costo-delete-dialog.component.html'
})
export class CostoDeleteDialogComponent {

    costo: Costo;

    constructor(
        private costoService: CostoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.costoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'costoListModification',
                content: 'Deleted an costo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-costo-delete-popup',
    template: ''
})
export class CostoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private costoPopupService: CostoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.costoPopupService
                .open(CostoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
