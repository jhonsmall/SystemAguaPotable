import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SystemAguaPotableSharedModule } from '../../shared';
import {
    LecturaMedidorService,
    LecturaMedidorPopupService,
    LecturaMedidorComponent,
    LecturaMedidorDetailComponent,
    LecturaMedidorDialogComponent,
    LecturaMedidorPopupComponent,
    LecturaMedidorDeletePopupComponent,
    LecturaMedidorDeleteDialogComponent,
    lecturaMedidorRoute,
    lecturaMedidorPopupRoute,
} from './';

const ENTITY_STATES = [
    ...lecturaMedidorRoute,
    ...lecturaMedidorPopupRoute,
];

@NgModule({
    imports: [
        SystemAguaPotableSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        LecturaMedidorComponent,
        LecturaMedidorDetailComponent,
        LecturaMedidorDialogComponent,
        LecturaMedidorDeleteDialogComponent,
        LecturaMedidorPopupComponent,
        LecturaMedidorDeletePopupComponent,
    ],
    entryComponents: [
        LecturaMedidorComponent,
        LecturaMedidorDialogComponent,
        LecturaMedidorPopupComponent,
        LecturaMedidorDeleteDialogComponent,
        LecturaMedidorDeletePopupComponent,
    ],
    providers: [
        LecturaMedidorService,
        LecturaMedidorPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SystemAguaPotableLecturaMedidorModule {}
