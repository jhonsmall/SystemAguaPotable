import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { LecturaMedidorComponent } from './lectura-medidor.component';
import { LecturaMedidorDetailComponent } from './lectura-medidor-detail.component';
import { LecturaMedidorPopupComponent } from './lectura-medidor-dialog.component';
import { LecturaMedidorDeletePopupComponent } from './lectura-medidor-delete-dialog.component';

export const lecturaMedidorRoute: Routes = [
    {
        path: 'lectura-medidor',
        component: LecturaMedidorComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.lecturaMedidor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'lectura-medidor/:id',
        component: LecturaMedidorDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.lecturaMedidor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const lecturaMedidorPopupRoute: Routes = [
    {
        path: 'lectura-medidor-new',
        component: LecturaMedidorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.lecturaMedidor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lectura-medidor/:id/edit',
        component: LecturaMedidorPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.lecturaMedidor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'lectura-medidor/:id/delete',
        component: LecturaMedidorDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.lecturaMedidor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
