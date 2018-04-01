import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EscalasDelMedidor } from './escalas-del-medidor.model';
import { EscalasDelMedidorPopupService } from './escalas-del-medidor-popup.service';
import { EscalasDelMedidorService } from './escalas-del-medidor.service';

@Component({
    selector: 'jhi-escalas-del-medidor-delete-dialog',
    templateUrl: './escalas-del-medidor-delete-dialog.component.html'
})
export class EscalasDelMedidorDeleteDialogComponent {

    escalasDelMedidor: EscalasDelMedidor;

    constructor(
        private escalasDelMedidorService: EscalasDelMedidorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.escalasDelMedidorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'escalasDelMedidorListModification',
                content: 'Deleted an escalasDelMedidor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-escalas-del-medidor-delete-popup',
    template: ''
})
export class EscalasDelMedidorDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private escalasDelMedidorPopupService: EscalasDelMedidorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.escalasDelMedidorPopupService
                .open(EscalasDelMedidorDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
