import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SystemAguaPotableUsuarioModule } from './usuario/usuario.module';
import { SystemAguaPotableServicioModule } from './servicio/servicio.module';
import { SystemAguaPotableClasificacionModule } from './clasificacion/clasificacion.module';
import { SystemAguaPotableEscalasDelMedidorModule } from './escalas-del-medidor/escalas-del-medidor.module';
import { SystemAguaPotableCostoModule } from './costo/costo.module';
import { SystemAguaPotableSectorModule } from './sector/sector.module';
import { SystemAguaPotableCostoMedidorModule } from './costo-medidor/costo-medidor.module';
import { SystemAguaPotableMedidorModule } from './medidor/medidor.module';
import { SystemAguaPotableLecturaMedidorModule } from './lectura-medidor/lectura-medidor.module';
import { SystemAguaPotableReciboModule } from './recibo/recibo.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SystemAguaPotableUsuarioModule,
        SystemAguaPotableServicioModule,
        SystemAguaPotableClasificacionModule,
        SystemAguaPotableEscalasDelMedidorModule,
        SystemAguaPotableCostoModule,
        SystemAguaPotableSectorModule,
        SystemAguaPotableCostoMedidorModule,
        SystemAguaPotableMedidorModule,
        SystemAguaPotableLecturaMedidorModule,
        SystemAguaPotableReciboModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SystemAguaPotableEntityModule {}
