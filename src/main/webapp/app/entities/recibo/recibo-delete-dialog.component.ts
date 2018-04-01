import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Recibo } from './recibo.model';
import { ReciboPopupService } from './recibo-popup.service';
import { ReciboService } from './recibo.service';

@Component({
    selector: 'jhi-recibo-delete-dialog',
    templateUrl: './recibo-delete-dialog.component.html'
})
export class ReciboDeleteDialogComponent {

    recibo: Recibo;

    constructor(
        private reciboService: ReciboService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reciboService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'reciboListModification',
                content: 'Deleted an recibo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-recibo-delete-popup',
    template: ''
})
export class ReciboDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reciboPopupService: ReciboPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.reciboPopupService
                .open(ReciboDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
