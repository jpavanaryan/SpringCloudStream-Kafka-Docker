import { BaseEntity } from './../../shared';

export class Project implements BaseEntity {
    constructor(
        public id?: number,
        public projectName?: string,
        public projectTitle?: string,
        public chargeCodeProjects?: BaseEntity[],
    ) {
    }
}
