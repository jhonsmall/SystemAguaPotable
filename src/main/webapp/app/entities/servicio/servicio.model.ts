import { BaseEntity } from './../../shared';

export const enum Tipo {
    'MANUAL',
    'MEDIDOR'
}

export class Servicio implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public norma?: string,
        public tipo?: Tipo,
        public servicioCostos?: BaseEntity[],
    ) {
    }
}
