import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ShelterPetModule } from './pet/pet.module';
import { ShelterPhotoModule } from './photo/photo.module';
import { ShelterShelterModule } from './shelter/shelter.module';
import { ShelterContactModule } from './contact/contact.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ShelterPetModule,
        ShelterPhotoModule,
        ShelterShelterModule,
        ShelterContactModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShelterEntityModule {}
