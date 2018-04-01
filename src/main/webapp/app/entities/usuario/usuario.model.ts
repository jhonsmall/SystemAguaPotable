import { BaseEntity } from './../../shared';

export const enum Estado {
    'ACTIVO',
    'INACTIVO'
}

export const enum Documento {
    'CI',
    'RUC',
    'PASAPORTE',
    'OTRO'
}

export const enum Sexo {
    'MASCULINO',
    'FEMENINO',
    'OTRO'
}

export class Usuario implements BaseEntity {
    constructor(
        public id?: number,
        public estado?: Estado,
        public documento?: Documento,
        public numero?: string,
        public nombres?: string,
        public apellidos?: string,
        public sexo?: Sexo,
        public direccion?: string,
        public telefono?: string,
        public email?: string,
        public usuarioRecibos?: BaseEntity[],
        public usuarioMedidors?: BaseEntity[],
    ) {
    }
}
