import { BaseEntity } from './../../shared';

export class Employee implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public middleName?: string,
        public salary?: number,
        public employeeProjects?: BaseEntity[],
        public address?: BaseEntity,
    ) {
    }
}
