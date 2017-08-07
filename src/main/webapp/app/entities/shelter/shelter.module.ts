import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShelterSharedModule } from '../../shared';
import {
    ShelterService,
    ShelterPopupService,
    ShelterComponent,
    ShelterDetailComponent,
    ShelterDialogComponent,
    ShelterPopupComponent,
    ShelterDeletePopupComponent,
    ShelterDeleteDialogComponent,
    shelterRoute,
    shelterPopupRoute,
} from './';

const ENTITY_STATES = [
    ...shelterRoute,
    ...shelterPopupRoute,
];

@NgModule({
    imports: [
        ShelterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ShelterComponent,
        ShelterDetailComponent,
        ShelterDialogComponent,
        ShelterDeleteDialogComponent,
        ShelterPopupComponent,
        ShelterDeletePopupComponent,
    ],
    entryComponents: [
        ShelterComponent,
        ShelterDialogComponent,
        ShelterPopupComponent,
        ShelterDeleteDialogComponent,
        ShelterDeletePopupComponent,
    ],
    providers: [
        ShelterService,
        ShelterPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShelterShelterModule {}
