import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Recibo } from './recibo.model';
import { ReciboService } from './recibo.service';

@Injectable()
export class ReciboPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private reciboService: ReciboService

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
                this.reciboService.find(id)
                    .subscribe((reciboResponse: HttpResponse<Recibo>) => {
                        const recibo: Recibo = reciboResponse.body;
                        recibo.fechagenera = this.datePipe
                            .transform(recibo.fechagenera, 'yyyy-MM-ddTHH:mm:ss');
                        recibo.fechapaga = this.datePipe
                            .transform(recibo.fechapaga, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.reciboModalRef(component, recibo);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.reciboModalRef(component, new Recibo());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    reciboModalRef(component: Component, recibo: Recibo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.recibo = recibo;
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
