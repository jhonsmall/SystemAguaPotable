import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Clasificacion } from './clasificacion.model';
import { ClasificacionService } from './clasificacion.service';

@Component({
    selector: 'jhi-clasificacion-detail',
    templateUrl: './clasificacion-detail.component.html'
})
export class ClasificacionDetailComponent implements OnInit, OnDestroy {

    clasificacion: Clasificacion;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private clasificacionService: ClasificacionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClasificacions();
    }

    load(id) {
        this.clasificacionService.find(id)
            .subscribe((clasificacionResponse: HttpResponse<Clasificacion>) => {
                this.clasificacion = clasificacionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClasificacions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'clasificacionListModification',
            (response) => this.load(this.clasificacion.id)
        );
    }
}
