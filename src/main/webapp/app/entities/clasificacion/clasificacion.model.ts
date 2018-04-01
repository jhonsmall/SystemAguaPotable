import { BaseEntity } from './../../shared';

export const enum Estado {
    'ACTIVO',
    'INACTIVO'
}

export class Clasificacion implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public estado?: Estado,
        public clasificacionCostos?: BaseEntity[],
        public clasificacionEscalasDelMedidors?: BaseEntity[],
        public clasificacionMedidors?: BaseEntity[],
    ) {
    }
}
