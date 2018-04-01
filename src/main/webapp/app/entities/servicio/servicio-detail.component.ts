import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Servicio } from './servicio.model';
import { ServicioService } from './servicio.service';

@Component({
    selector: 'jhi-servicio-detail',
    templateUrl: './servicio-detail.component.html'
})
export class ServicioDetailComponent implements OnInit, OnDestroy {

    servicio: Servicio;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private servicioService: ServicioService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInServicios();
    }

    load(id) {
        this.servicioService.find(id)
            .subscribe((servicioResponse: HttpResponse<Servicio>) => {
                this.servicio = servicioResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInServicios() {
        this.eventSubscriber = this.eventManager.subscribe(
            'servicioListModification',
            (response) => this.load(this.servicio.id)
        );
    }
}
