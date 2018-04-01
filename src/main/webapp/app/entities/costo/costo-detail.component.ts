import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Costo } from './costo.model';
import { CostoService } from './costo.service';

@Component({
    selector: 'jhi-costo-detail',
    templateUrl: './costo-detail.component.html'
})
export class CostoDetailComponent implements OnInit, OnDestroy {

    costo: Costo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private costoService: CostoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCostos();
    }

    load(id) {
        this.costoService.find(id)
            .subscribe((costoResponse: HttpResponse<Costo>) => {
                this.costo = costoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCostos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'costoListModification',
            (response) => this.load(this.costo.id)
        );
    }
}
