import { BaseEntity } from './../../shared';

export class ChargeCodeProject implements BaseEntity {
    constructor(
        public id?: number,
        public chargecode?: BaseEntity,
        public project?: BaseEntity,
    ) {
    }
}
