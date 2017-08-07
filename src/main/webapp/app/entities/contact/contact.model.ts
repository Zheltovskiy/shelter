import { BaseEntity } from './../../shared';

const enum ContactType {
    'PHONE',
    'EMAIL',
    'INSTAGRAM',
    'FACEBOOK',
    'VK',
    'OK'
}

export class Contact implements BaseEntity {
    constructor(
        public id?: string,
        public type?: ContactType,
        public link?: string,
    ) {
    }
}
