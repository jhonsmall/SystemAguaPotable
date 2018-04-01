import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { EscalasDelMedidor } from './escalas-del-medidor.model';
import { EscalasDelMedidorService } from './escalas-del-medidor.service';

@Injectable()
export class EscalasDelMedidorPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private escalasDelMedidorService: EscalasDelMedidorService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.escalasDelMedidorService.find(id)
                    .subscribe((escalasDelMedidorResponse: HttpResponse<EscalasDelMedidor>) => {
                        const escalasDelMedidor: EscalasDelMedidor = escalasDelMedidorResponse.body;
                        escalasDelMedidor.fecha = this.datePipe
                            .transform(escalasDelMedidor.fecha, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.escalasDelMedidorModalRef(component, escalasDelMedidor);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.escalasDelMedidorModalRef(component, new EscalasDelMedidor());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    escalasDelMedidorModalRef(component: Component, escalasDelMedidor: EscalasDelMedidor): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.escalasDelMedidor = escalasDelMedidor;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
