import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EscalasDelMedidor } from './escalas-del-medidor.model';
import { EscalasDelMedidorService } from './escalas-del-medidor.service';

@Component({
    selector: 'jhi-escalas-del-medidor-detail',
    templateUrl: './escalas-del-medidor-detail.component.html'
})
export class EscalasDelMedidorDetailComponent implements OnInit, OnDestroy {

    escalasDelMedidor: EscalasDelMedidor;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private escalasDelMedidorService: EscalasDelMedidorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEscalasDelMedidors();
    }

    load(id) {
        this.escalasDelMedidorService.find(id)
            .subscribe((escalasDelMedidorResponse: HttpResponse<EscalasDelMedidor>) => {
                this.escalasDelMedidor = escalasDelMedidorResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEscalasDelMedidors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'escalasDelMedidorListModification',
            (response) => this.load(this.escalasDelMedidor.id)
        );
    }
}
