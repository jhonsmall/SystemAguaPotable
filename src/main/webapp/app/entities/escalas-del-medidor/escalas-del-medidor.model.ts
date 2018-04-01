import { BaseEntity } from './../../shared';

export class EscalasDelMedidor implements BaseEntity {
    constructor(
        public id?: number,
        public inicio?: number,
        public fin?: number,
        public fecha?: any,
        public clasificacionId?: number,
    ) {
    }
}
