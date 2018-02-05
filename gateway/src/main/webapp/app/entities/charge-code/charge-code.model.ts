import { BaseEntity } from './../../shared';

export class ChargeCode implements BaseEntity {
    constructor(
        public id?: number,
        public chargeCodeName?: string,
        public chargeCodeLocation?: string,
        public chargeCodeStartDate?: any,
        public chargeCodeEndDate?: any,
        public chargeCodeProjects?: BaseEntity[],
    ) {
    }
}
