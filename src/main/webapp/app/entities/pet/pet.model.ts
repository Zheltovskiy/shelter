import { BaseEntity } from './../../shared';

const enum PetType {
    'CAT',
    'DOG',
    'OTHER'
}

export class Pet implements BaseEntity {
    constructor(
        public id?: string,
        public name?: string,
        public color?: string,
        public ageInMonths?: number,
        public type?: PetType,
        public otherTypeName?: string,
        public mainPhotoContentType?: string,
        public mainPhoto?: any,
    ) {
    }
}
