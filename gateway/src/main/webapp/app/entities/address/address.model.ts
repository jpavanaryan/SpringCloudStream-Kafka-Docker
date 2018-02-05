import { BaseEntity } from './../../shared';

export class Address implements BaseEntity {
    constructor(
        public id?: number,
        public streetName?: string,
        public apartmentOrHouseNumber?: string,
        public city?: string,
        public zipcode?: number,
        public state?: string,
        public country?: string,
    ) {
    }
}
