import { BaseEntity } from './../../shared';

export class EmployeeProject implements BaseEntity {
    constructor(
        public id?: number,
        public employee?: BaseEntity,
        public project?: BaseEntity,
    ) {
    }
}
