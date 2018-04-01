import { BaseEntity } from './../../shared';

export class Sector implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public descripcion?: string,
        public sectorCostos?: BaseEntity[],
        public sectorMedidors?: BaseEntity[],
    ) {
    }
}
