import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ClasificacionComponent } from './clasificacion.component';
import { ClasificacionDetailComponent } from './clasificacion-detail.component';
import { ClasificacionPopupComponent } from './clasificacion-dialog.component';
import { ClasificacionDeletePopupComponent } from './clasificacion-delete-dialog.component';

export const clasificacionRoute: Routes = [
    {
        path: 'clasificacion',
        component: ClasificacionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.clasificacion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'clasificacion/:id',
        component: ClasificacionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.clasificacion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clasificacionPopupRoute: Routes = [
    {
        path: 'clasificacion-new',
        component: ClasificacionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.clasificacion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clasificacion/:id/edit',
        component: ClasificacionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.clasificacion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'clasificacion/:id/delete',
        component: ClasificacionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'systemAguaPotableApp.clasificacion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
