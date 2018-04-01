import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CostoMedidor } from './costo-medidor.model';
import { CostoMedidorService } from './costo-medidor.service';

@Component({
    selector: 'jhi-costo-medidor-detail',
    templateUrl: './costo-medidor-detail.component.html'
})
export class CostoMedidorDetailComponent implements OnInit, OnDestroy {

    costoMedidor: CostoMedidor;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private costoMedidorService: CostoMedidorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCostoMedidors();
    }

    load(id) {
        this.costoMedidorService.find(id)
            .subscribe((costoMedidorResponse: HttpResponse<CostoMedidor>) => {
                this.costoMedidor = costoMedidorResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCostoMedidors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'costoMedidorListModification',
            (response) => this.load(this.costoMedidor.id)
        );
    }
}
