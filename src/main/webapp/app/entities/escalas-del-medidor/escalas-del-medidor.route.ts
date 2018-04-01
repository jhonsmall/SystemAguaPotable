import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EscalasDelMedidorComponent } from './escalas-del-medidor.component';
import { EscalasDelMedidorDetailComponent } from './escalas-del-medidor-detail.component';
import { EscalasDelMedidorPopupComponent } from './escalas-del-medidor-dialog.component';
import { EscalasDelMedidorDeletePopupComponent } from './escalas-del-medidor-delete-dialog.component';

export const escalasDelMedidorRoute: Routes = [
    {
        path: 'escalas-del-medidor',
        component: EscalasDelMedidorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.escalasDelMedidor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'escalas-del-medidor/:id',
        component: EscalasDelMedidorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.escalasDelMedidor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const escalasDelMedidorPopupRoute: Routes = [
    {
        path: 'escalas-del-medidor-new',
        component: EscalasDelMedidorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.escalasDelMedidor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'escalas-del-medidor/:id/edit',
        component: EscalasDelMedidorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.escalasDelMedidor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'escalas-del-medidor/:id/delete',
        component: EscalasDelMedidorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.escalasDelMedidor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
