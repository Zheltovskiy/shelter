import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShelterSharedModule } from '../../shared';
import {
    PetService,
    PetPopupService,
    PetComponent,
    PetDetailComponent,
    PetDialogComponent,
    PetPopupComponent,
    PetDeletePopupComponent,
    PetDeleteDialogComponent,
    petRoute,
    petPopupRoute,
} from './';

const ENTITY_STATES = [
    ...petRoute,
    ...petPopupRoute,
];

@NgModule({
    imports: [
        ShelterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PetComponent,
        PetDetailComponent,
        PetDialogComponent,
        PetDeleteDialogComponent,
        PetPopupComponent,
        PetDeletePopupComponent,
    ],
    entryComponents: [
        PetComponent,
        PetDialogComponent,
        PetPopupComponent,
        PetDeleteDialogComponent,
        PetDeletePopupComponent,
    ],
    providers: [
        PetService,
        PetPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShelterPetModule {}
