import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { LecturaMedidor } from './lectura-medidor.model';
import { LecturaMedidorService } from './lectura-medidor.service';

@Component({
    selector: 'jhi-lectura-medidor-detail',
    templateUrl: './lectura-medidor-detail.component.html'
})
export class LecturaMedidorDetailComponent implements OnInit, OnDestroy {

    lecturaMedidor: LecturaMedidor;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private lecturaMedidorService: LecturaMedidorService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLecturaMedidors();
    }

    load(id) {
        this.lecturaMedidorService.find(id)
            .subscribe((lecturaMedidorResponse: HttpResponse<LecturaMedidor>) => {
                this.lecturaMedidor = lecturaMedidorResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLecturaMedidors() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lecturaMedidorListModification',
            (response) => this.load(this.lecturaMedidor.id)
        );
    }
}
