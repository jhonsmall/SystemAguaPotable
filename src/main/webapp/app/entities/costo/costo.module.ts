import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SystemAguaPotableSharedModule } from '../../shared';
import {
    CostoService,
    CostoPopupService,
    CostoComponent,
    CostoDetailComponent,
    CostoDialogComponent,
    CostoPopupComponent,
    CostoDeletePopupComponent,
    CostoDeleteDialogComponent,
    costoRoute,
    costoPopupRoute,
} from './';

const ENTITY_STATES = [
    ...costoRoute,
    ...costoPopupRoute,
];

@NgModule({
    imports: [
        SystemAguaPotableSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CostoComponent,
        CostoDetailComponent,
        CostoDialogComponent,
        CostoDeleteDialogComponent,
        CostoPopupComponent,
        CostoDeletePopupComponent,
    ],
    entryComponents: [
        CostoComponent,
        CostoDialogComponent,
        CostoPopupComponent,
        CostoDeleteDialogComponent,
        CostoDeletePopupComponent,
    ],
    providers: [
        CostoService,
        CostoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SystemAguaPotableCostoModule {}
