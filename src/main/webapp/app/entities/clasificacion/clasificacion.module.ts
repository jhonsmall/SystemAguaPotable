import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SystemAguaPotableSharedModule } from '../../shared';
import {
    ClasificacionService,
    ClasificacionPopupService,
    ClasificacionComponent,
    ClasificacionDetailComponent,
    ClasificacionDialogComponent,
    ClasificacionPopupComponent,
    ClasificacionDeletePopupComponent,
    ClasificacionDeleteDialogComponent,
    clasificacionRoute,
    clasificacionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...clasificacionRoute,
    ...clasificacionPopupRoute,
];

@NgModule({
    imports: [
        SystemAguaPotableSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ClasificacionComponent,
        ClasificacionDetailComponent,
        ClasificacionDialogComponent,
        ClasificacionDeleteDialogComponent,
        ClasificacionPopupComponent,
        ClasificacionDeletePopupComponent,
    ],
    entryComponents: [
        ClasificacionComponent,
        ClasificacionDialogComponent,
        ClasificacionPopupComponent,
        ClasificacionDeleteDialogComponent,
        ClasificacionDeletePopupComponent,
    ],
    providers: [
        ClasificacionService,
        ClasificacionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SystemAguaPotableClasificacionModule {}
