import { BaseEntity } from './../../shared';

export class Shelter implements BaseEntity {
    constructor(
        public id?: string,
        public name?: string,
        public address?: string,
        public latitude?: number,
        public longitude?: number,
    ) {
    }
}
