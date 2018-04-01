import { BaseEntity } from './../../shared';

export class Medidor implements BaseEntity {
    constructor(
        public id?: number,
        public numeromedidor?: number,
        public fechaadquirio?: any,
        public fechaactual?: any,
        public medidorCostoMedidors?: BaseEntity[],
        public medidorLecturaMedidors?: BaseEntity[],
        public usuarioId?: number,
        public sectorId?: number,
        public clasificacionId?: number,
    ) {
    }
}
