import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ReciboComponent } from './recibo.component';
import { ReciboDetailComponent } from './recibo-detail.component';
import { ReciboPopupComponent } from './recibo-dialog.component';
import { ReciboDeletePopupComponent } from './recibo-delete-dialog.component';

export const reciboRoute: Routes = [
    {
        path: 'recibo',
        component: ReciboComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.recibo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'recibo/:id',
        component: ReciboDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.recibo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reciboPopupRoute: Routes = [
    {
        path: 'recibo-new',
        component: ReciboPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.recibo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'recibo/:id/edit',
        component: ReciboPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.recibo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'recibo/:id/delete',
        component: ReciboDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.recibo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
