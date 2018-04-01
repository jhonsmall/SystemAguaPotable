import { BaseEntity } from './../../shared';

export const enum Estado {
    'ACTIVO',
    'INACTIVO'
}

export class Recibo implements BaseEntity {
    constructor(
        public id?: number,
        public numero?: number,
        public estado?: Estado,
        public pagoanterior?: number,
        public pagoactual?: number,
        public total?: number,
        public fechagenera?: any,
        public fechapaga?: any,
        public anio?: number,
        public mes?: number,
        public usuarioId?: number,
        public lecturaMedidors?: BaseEntity[],
    ) {
    }
}
