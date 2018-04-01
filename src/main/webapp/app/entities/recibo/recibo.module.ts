import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SystemAguaPotableSharedModule } from '../../shared';
import {
    ReciboService,
    ReciboPopupService,
    ReciboComponent,
    ReciboDetailComponent,
    ReciboDialogComponent,
    ReciboPopupComponent,
    ReciboDeletePopupComponent,
    ReciboDeleteDialogComponent,
    reciboRoute,
    reciboPopupRoute,
} from './';

const ENTITY_STATES = [
    ...reciboRoute,
    ...reciboPopupRoute,
];

@NgModule({
    imports: [
        SystemAguaPotableSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ReciboComponent,
        ReciboDetailComponent,
        ReciboDialogComponent,
        ReciboDeleteDialogComponent,
        ReciboPopupComponent,
        ReciboDeletePopupComponent,
    ],
    entryComponents: [
        ReciboComponent,
        ReciboDialogComponent,
        ReciboPopupComponent,
        ReciboDeleteDialogComponent,
        ReciboDeletePopupComponent,
    ],
    providers: [
        ReciboService,
        ReciboPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SystemAguaPotableReciboModule {}
