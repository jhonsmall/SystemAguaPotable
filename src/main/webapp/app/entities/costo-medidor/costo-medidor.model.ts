import { BaseEntity } from './../../shared';

export const enum Estado {
    'ACTIVO',
    'INACTIVO'
}

export class CostoMedidor implements BaseEntity {
    constructor(
        public id?: number,
        public fecha?: any,
        public estado?: Estado,
        public costoId?: number,
        public medidorId?: number,
    ) {
    }
}
