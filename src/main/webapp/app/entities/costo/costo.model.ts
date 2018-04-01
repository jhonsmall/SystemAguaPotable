import { BaseEntity } from './../../shared';

export class Costo implements BaseEntity {
    constructor(
        public id?: number,
        public cuota?: number,
        public fecha?: any,
        public costoCostoMedidors?: BaseEntity[],
        public servicioId?: number,
        public sectorId?: number,
        public clasificacionId?: number,
    ) {
    }
}
