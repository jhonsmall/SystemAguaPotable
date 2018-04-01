import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Recibo } from './recibo.model';
import { ReciboService } from './recibo.service';

@Component({
    selector: 'jhi-recibo-detail',
    templateUrl: './recibo-detail.component.html'
})
export class ReciboDetailComponent implements OnInit, OnDestroy {

    recibo: Recibo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private reciboService: ReciboService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRecibos();
    }

    load(id) {
        this.reciboService.find(id)
            .subscribe((reciboResponse: HttpResponse<Recibo>) => {
                this.recibo = reciboResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRecibos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'reciboListModification',
            (response) => this.load(this.recibo.id)
        );
    }
}
