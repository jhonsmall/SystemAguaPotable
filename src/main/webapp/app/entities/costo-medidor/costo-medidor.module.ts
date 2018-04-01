import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SystemAguaPotableSharedModule } from '../../shared';
import {
    CostoMedidorService,
    CostoMedidorPopupService,
    CostoMedidorComponent,
    CostoMedidorDetailComponent,
    CostoMedidorDialogComponent,
    CostoMedidorPopupComponent,
    CostoMedidorDeletePopupComponent,
    CostoMedidorDeleteDialogComponent,
    costoMedidorRoute,
    costoMedidorPopupRoute,
} from './';

const ENTITY_STATES = [
    ...costoMedidorRoute,
    ...costoMedidorPopupRoute,
];

@NgModule({
    imports: [
        SystemAguaPotableSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CostoMedidorComponent,
        CostoMedidorDetailComponent,
        CostoMedidorDialogComponent,
        CostoMedidorDeleteDialogComponent,
        CostoMedidorPopupComponent,
        CostoMedidorDeletePopupComponent,
    ],
    entryComponents: [
        CostoMedidorComponent,
        CostoMedidorDialogComponent,
        CostoMedidorPopupComponent,
        CostoMedidorDeleteDialogComponent,
        CostoMedidorDeletePopupComponent,
    ],
    providers: [
        CostoMedidorService,
        CostoMedidorPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SystemAguaPotableCostoMedidorModule {}
