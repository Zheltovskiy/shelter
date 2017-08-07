import { BaseEntity } from './../../shared';

export class Photo implements BaseEntity {
    constructor(
        public id?: string,
        public imageContentType?: string,
        public image?: any,
    ) {
    }
}
