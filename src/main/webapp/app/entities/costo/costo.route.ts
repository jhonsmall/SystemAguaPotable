import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CostoComponent } from './costo.component';
import { CostoDetailComponent } from './costo-detail.component';
import { CostoPopupComponent } from './costo-dialog.component';
import { CostoDeletePopupComponent } from './costo-delete-dialog.component';

export const costoRoute: Routes = [
    {
        path: 'costo',
        component: CostoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.costo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'costo/:id',
        component: CostoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.costo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const costoPopupRoute: Routes = [
    {
        path: 'costo-new',
        component: CostoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.costo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'costo/:id/edit',
        component: CostoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.costo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'costo/:id/delete',
        component: CostoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.costo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
