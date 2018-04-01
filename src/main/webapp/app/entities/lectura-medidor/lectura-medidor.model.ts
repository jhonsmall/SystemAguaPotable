import { BaseEntity } from './../../shared';

export const enum Estado {
    'ACTIVO',
    'INACTIVO'
}

export class LecturaMedidor implements BaseEntity {
    constructor(
        public id?: number,
        public lecturainicial?: number,
        public lecturafinal?: number,
        public estado?: Estado,
        public fecha?: any,
        public anio?: number,
        public mes?: number,
        public descripcion?: string,
        public lecturamedidorRecibos?: BaseEntity[],
        public medidorId?: number,
    ) {
    }
}
