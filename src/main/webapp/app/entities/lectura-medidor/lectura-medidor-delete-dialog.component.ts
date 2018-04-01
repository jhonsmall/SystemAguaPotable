import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LecturaMedidor } from './lectura-medidor.model';
import { LecturaMedidorPopupService } from './lectura-medidor-popup.service';
import { LecturaMedidorService } from './lectura-medidor.service';

@Component({
    selector: 'jhi-lectura-medidor-delete-dialog',
    templateUrl: './lectura-medidor-delete-dialog.component.html'
})
export class LecturaMedidorDeleteDialogComponent {

    lecturaMedidor: LecturaMedidor;

    constructor(
        private lecturaMedidorService: LecturaMedidorService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.lecturaMedidorService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'lecturaMedidorListModification',
                content: 'Deleted an lecturaMedidor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lectura-medidor-delete-popup',
    template: ''
})
export class LecturaMedidorDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lecturaMedidorPopupService: LecturaMedidorPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.lecturaMedidorPopupService
                .open(LecturaMedidorDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
