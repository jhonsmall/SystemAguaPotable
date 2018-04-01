import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CostoMedidorComponent } from './costo-medidor.component';
import { CostoMedidorDetailComponent } from './costo-medidor-detail.component';
import { CostoMedidorPopupComponent } from './costo-medidor-dialog.component';
import { CostoMedidorDeletePopupComponent } from './costo-medidor-delete-dialog.component';

export const costoMedidorRoute: Routes = [
    {
        path: 'costo-medidor',
        component: CostoMedidorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.costoMedidor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'costo-medidor/:id',
        component: CostoMedidorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.costoMedidor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const costoMedidorPopupRoute: Routes = [
    {
        path: 'costo-medidor-new',
        component: CostoMedidorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.costoMedidor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'costo-medidor/:id/edit',
        component: CostoMedidorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.costoMedidor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'costo-medidor/:id/delete',
        component: CostoMedidorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.costoMedidor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
