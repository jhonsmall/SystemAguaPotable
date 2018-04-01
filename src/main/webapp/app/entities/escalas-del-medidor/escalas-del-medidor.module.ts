import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SystemAguaPotableSharedModule } from '../../shared';
import {
    EscalasDelMedidorService,
    EscalasDelMedidorPopupService,
    EscalasDelMedidorComponent,
    EscalasDelMedidorDetailComponent,
    EscalasDelMedidorDialogComponent,
    EscalasDelMedidorPopupComponent,
    EscalasDelMedidorDeletePopupComponent,
    EscalasDelMedidorDeleteDialogComponent,
    escalasDelMedidorRoute,
    escalasDelMedidorPopupRoute,
} from './';

const ENTITY_STATES = [
    ...escalasDelMedidorRoute,
    ...escalasDelMedidorPopupRoute,
];

@NgModule({
    imports: [
        SystemAguaPotableSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EscalasDelMedidorComponent,
        EscalasDelMedidorDetailComponent,
        EscalasDelMedidorDialogComponent,
        EscalasDelMedidorDeleteDialogComponent,
        EscalasDelMedidorPopupComponent,
        EscalasDelMedidorDeletePopupComponent,
    ],
    entryComponents: [
        EscalasDelMedidorComponent,
        EscalasDelMedidorDialogComponent,
        EscalasDelMedidorPopupComponent,
        EscalasDelMedidorDeleteDialogComponent,
        EscalasDelMedidorDeletePopupComponent,
    ],
    providers: [
        EscalasDelMedidorService,
        EscalasDelMedidorPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SystemAguaPotableEscalasDelMedidorModule {}
