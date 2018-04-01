import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { CostoMedidor } from './costo-medidor.model';
import { CostoMedidorService } from './costo-medidor.service';

@Injectable()
export class CostoMedidorPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private costoMedidorService: CostoMedidorService

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
                this.costoMedidorService.find(id)
                    .subscribe((costoMedidorResponse: HttpResponse<CostoMedidor>) => {
                        const costoMedidor: CostoMedidor = costoMedidorResponse.body;
                        costoMedidor.fecha = this.datePipe
                            .transform(costoMedidor.fecha, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.costoMedidorModalRef(component, costoMedidor);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.costoMedidorModalRef(component, new CostoMedidor());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    costoMedidorModalRef(component: Component, costoMedidor: CostoMedidor): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.costoMedidor = costoMedidor;
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
